package org.blockserver.player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.blockserver.BlockServer;
import org.blockserver.Server;
import org.blockserver.cmd.CommandIssuer;
import org.blockserver.entity.Entity;
import org.blockserver.entity.EntityType;
import org.blockserver.item.Inventory;
import org.blockserver.math.Vector3d;
import org.blockserver.network.minecraft.AddPlayerPacket;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.network.minecraft.ChatPacket;
import org.blockserver.network.minecraft.ClientConnectPacket;
import org.blockserver.network.minecraft.ClientHandShakePacket;
import org.blockserver.network.minecraft.Disconnect;
import org.blockserver.network.minecraft.LoginPacket;
import org.blockserver.network.minecraft.LoginStatusPacket;
import org.blockserver.network.minecraft.MessagePacket;
import org.blockserver.network.minecraft.PacketsID;
import org.blockserver.network.minecraft.PingPacket;
import org.blockserver.network.minecraft.PongPacket;
import org.blockserver.network.minecraft.ServerHandshakePacket;
import org.blockserver.network.minecraft.StartGamePacket;
import org.blockserver.network.raknet.ACKPacket;
import org.blockserver.network.raknet.AcknowledgePacket;
import org.blockserver.network.raknet.CustomPacket;
import org.blockserver.network.raknet.InternalPacket;
import org.blockserver.network.raknet.NACKPacket;
import org.blockserver.scheduler.CallbackTask;

public class Player extends Entity implements CommandIssuer{
	private String name;
	private String ip;
	private int port;

	private int lastSequenceNum;
	private int sequenceNum;
	private short mtuSize; // Maximum Transport Unit Size
	private int messageIndex;
	private CustomPacket queue;
	private List<Integer> ACKQueue; // Received Packet Queue
	private List<Integer> NACKQueue; // Not received packet queue
	private Map<Integer, CustomPacket> recoveryQueue; // Packet sent queue to be used if not received
	private long clientID; // Client ID From MCPE Client

	private int maxHealth;
	private Server server;
	protected Inventory inventory;

	public Player(Server server, String ip, int port, short mtu, long clientID){
		super(0, 0, 0, null);
		this.ip = ip.replace("/", "");
		this.port = port;
		mtuSize = mtu;
		this.clientID = clientID;
		lastSequenceNum = sequenceNum = messageIndex = 0;
		this.server = server;
		queue = new CustomPacket();
		ACKQueue = new ArrayList<Integer>();
		NACKQueue = new ArrayList<Integer>();
		recoveryQueue = new HashMap<Integer, CustomPacket>();
		try{
			server.getScheduler().addTask(new CallbackTask(this, "update", 10, true)); // do this with the entity context?
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void update(long ticks){
		if(this.ACKQueue.size() > 0){
			int[] array = new int[this.ACKQueue.size()];
			int offset = 0;
			for(Integer i: ACKQueue){
				array[offset++] = i;
			}
			ACKPacket pck = new ACKPacket(array);
			pck.encode();
			server.sendPacket(pck.getBuffer().array(), ip, port);
		}
		if(NACKQueue.size() > 0){
			int[] array = new int[NACKQueue.size()];
			int offset = 0;
			for(Integer i: NACKQueue){
				array[offset++] = i;
			}
			NACKPacket pck = new NACKPacket(array);
			pck.encode();
			server.sendPacket(pck.getBuffer().array(), ip, port);
		}
		if(queue.packets.size() > 0){
			queue.encode();
			server.sendPacket(queue.getBuffer().array(), ip, port);
			recoveryQueue.put(queue.sequenceNumber, queue);
			queue.packets.clear();
		}
	}

	public void addToQueue(BaseDataPacket pck){
		pck.encode();
		BlockServer.Debugging.logSentDataPacket(pck, this);
		InternalPacket ipck = new InternalPacket();
		ipck.buffer = pck.getBuffer().array();
		ipck.reliability = 2;
		ipck.messageIndex = messageIndex++;
		ipck.toBinary();
		if(queue.getLength() >= mtuSize){
			queue.sequenceNumber = sequenceNum++;
			queue.encode();
			server.sendPacket(queue.getBuffer().array(), ip, port);
			queue.packets.clear();
		}
		queue.packets.add(ipck);
	}
	public void handlePacket(CustomPacket pck){
		if(pck.sequenceNumber - this.lastSequenceNum == 1){
			lastSequenceNum = pck.sequenceNumber;
		}
		else{
			for(int i = this.lastSequenceNum; i < pck.sequenceNumber; ++i){
				NACKQueue.add(i);
			}
		}
		ACKQueue.add(pck.sequenceNumber);
		for(InternalPacket ipck : pck.packets){
			BlockServer.Debugging.logReceivedInternalPacket(ipck, this);
			switch (ipck.buffer[0]){
				case PacketsID.PING: //PING Packet
					PingPacket pp = new PingPacket(ipck.buffer);
					pp.decode();
					PongPacket reply = new PongPacket(pp.pingID);
					addToQueue(reply);
					break;
				case PacketsID.CLIENT_CONNECT: // 0x09. Use the constants class
					ClientConnectPacket ccp = new ClientConnectPacket(ipck.buffer);
					ccp.decode();
					//Send a ServerHandshake packet
					ServerHandshakePacket shp = new ServerHandshakePacket(this.port, ccp.session);
					addToQueue(shp);
					break;
				case PacketsID.CLIENT_HANDSHAKE:
					ClientHandShakePacket chs = new ClientHandShakePacket(ipck.buffer);
					chs.decode();
					break;
				case PacketsID.LOGIN:
					LoginPacket lp = new LoginPacket(ipck.buffer);
					server.getLogger().info("Login Packet: %d", ipck.buffer.length);
					lp.decode();
					if(lp.protocol != PacketsID.CURRENT_PROTOCOL || lp.protocol2 != PacketsID.CURRENT_PROTOCOL){
						if(lp.protocol < PacketsID.CURRENT_PROTOCOL || lp.protocol2 < PacketsID.CURRENT_PROTOCOL){
							addToQueue(new LoginStatusPacket(1)); // Client outdated
							close("Wrong Protocol: Client is outdated.");
						}

						if(lp.protocol > PacketsID.CURRENT_PROTOCOL || lp.protocol2 > PacketsID.CURRENT_PROTOCOL){
							addToQueue(new LoginStatusPacket(2)); // Server outdated
							close("Wrong Protocol: Server is outdated.");
						}
					}
					addToQueue(new LoginStatusPacket(0)); // No error with the protocol.
					/*
					ArrayList<Player> players = server.getConnectedPlayers();
					server.getLogger().info("Size is "+players.size());
					for(int i = 0; i <= players.size(); i++){
						Player p = players.get(i);
						if(p.getName().equalsIgnoreCase(lp.username)){
							//Name conflict, disconnect the original player
							server.getLogger().info(p.getName()+"("+p.getIP()+":"+p.getPort()+") disconnected: Name Conflict.");
							server.removePlayer(p);
							server.getLogger().info(server.getPlayersConnected()+" players are connected.");
							p.close("Another user logged in with your name.");
						}
					}
					*/
					if(lp.username.length() < 3 || lp.username.length() > 15){
						close("Username is not valid.");
					}
					else{
						name = lp.username;
						server.getLogger().info("%s (%s:%d) logged in with a fake entity ID.", name, ip, port);

//						login();
						//Once we get World generation up, uncomment this:
						/*
						StartGamePacket sgp = new StartGamePacket(server.getDefaultLevel(), this.entityID);
						addToQueue(sgp);
						*/
						StartGamePacket sgp = new StartGamePacket(new Vector3d(100d, 2d, 100d), 1, 100, 1);
						addToQueue(sgp);
						sendChatArgs(server.getMOTD());

						server.getChatMgr().broadcast(name+" joined the game.");

						for(Player other: server.getConnectedPlayers()){
							if(!(other.clientID == this.clientID)){
								spawnPlayer(other);
							}
						}
					}
					break;
				case PacketsID.DISCONNECT:
					disconnect("disconnected by client");
					break;
				case PacketsID.CHAT:
					server.getLogger().info("ChatPacket used for chat (Unusual).");
					ChatPacket cpk = new ChatPacket(ipck.buffer);
					cpk.decode();
					server.getChatMgr().handleChat(this, cpk.message);
					break;
				case PacketsID.MESSAGE:
					MessagePacket mpk = new MessagePacket(ipck.buffer);
					mpk.decode();
					server.getChatMgr().handleChat(this, mpk.msg);
					break;
				default:
					server.getLogger().debug("Unsupported packet recived: %02x", ipck.buffer[0]);
			}
		}
	}

	public void handleAcknowledgePackets(AcknowledgePacket pck){ // ACK and NACK
		pck.decode();
		if(pck instanceof ACKPacket){ // When we receive a ACK Packet then
			for(int i: pck.sequenceNumbers){
				//server.getLogger().info("ACK Packet Received Seq: %d", i);
				recoveryQueue.remove(i);
			}
		}
		else if(pck instanceof NACKPacket){
			for(int i: pck.sequenceNumbers){
				server.getLogger().info("NACK Packet Received Seq: %d", i);
				server.sendPacket(this.recoveryQueue.get(i).getBuffer().array(), this.ip, this.port);
			}
		}
		else{
			server.getLogger().error("Unknown Acknowledge Packet: %02x", pck.buffer[0]);
		}
	}

	// API methods outside networking
	public void sendChat(String msg, String src){
		MessagePacket mpkt = new MessagePacket(msg);
		addToQueue(mpkt); // be aware of the message-too-long exception
	}
	public void sendChatArgs(String msg, Object... args){
		sendChat(String.format(msg, args));
	}
	@Override
	public void sendChat(String msg){
		sendChat(msg, "");
	}
	public void spawnPlayer(Player other){
		addToQueue(new AddPlayerPacket(other));
	}

	protected void login(){
		PlayerData data = server.getPlayerDatabase().load(this);
		setCoords(data.getCoords());
		this.level = data.getLevel(); // validated!
//		start(server);
	}
	public void close(String reason){
		if(reason != null){
			sendChat(reason);
		}
		addToQueue(new Disconnect());
		disconnect(String.format("kicked (%s)", reason));
	}
	protected void disconnect(String reason){
		server.getLogger().info("%s (%s:%d) disconnected: %s.", name, ip, port, reason);
		server.getChatMgr().broadcast(name+" left the game.");
		server.removePlayer(this);
		server.getPlayerDatabase().save(new PlayerData(level, this, getIName(), getInventory()));
	}

	public InetAddress getAddress(){
		try{
			return InetAddress.getByName(ip);
		}
		catch(UnknownHostException e){
			e.printStackTrace();
			return null;
		}
	}
	public String getIP(){
		return ip;
	}
	public int getPort(){
		return port;
	}
	public String getIName(){
		return name.toLowerCase(Locale.US);
	}
	public String getName(){
		return name;
	}
	public String getIdentifier(){ // why not just use EID?
		return ip + Integer.toString(port);
	}
	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}
	@Override
	public int getMaxHealth(){
		return maxHealth;
	}
//	@Override
	public Inventory getInventory(){
		return inventory;
	}
	public long getClientID() {
		return clientID;
	}

	@Override
	protected void broadcastMotion(){
		// TODO Auto-generated method stub
	}

	@Override
	public void sudoCommand(String line){
		server.getCmdManager().runCommand(this, line.substring(1));
	}
	@Override
	public int getHelpLines(){
		return 5; // TODO customization should be permitted
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	public String getEOL(){
		return "\n";
	}
}
