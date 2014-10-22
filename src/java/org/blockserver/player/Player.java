package org.blockserver.player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.blockserver.BlockServer;
import org.blockserver.Server;
import org.blockserver.cmd.CommandIssuer;
import org.blockserver.entity.Entity;
import org.blockserver.item.Inventory;
import org.blockserver.level.generator.Generator;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.IChunk;
import org.blockserver.math.Vector3;
import org.blockserver.math.Vector3d;
import org.blockserver.network.minecraft.AddPlayerPacket;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.network.minecraft.ClientConnectPacket;
import org.blockserver.network.minecraft.ClientHandShakePacket;
import org.blockserver.network.minecraft.DisconnectPacket;
import org.blockserver.network.minecraft.FullChunkDataPacket;
import org.blockserver.network.minecraft.LoginPacket;
import org.blockserver.network.minecraft.LoginStatusPacket;
import org.blockserver.network.minecraft.MessagePacket;
import org.blockserver.network.minecraft.MovePlayerPacket;
import org.blockserver.network.minecraft.PacketIDs;
import org.blockserver.network.minecraft.PingPacket;
import org.blockserver.network.minecraft.PongPacket;
import org.blockserver.network.minecraft.ServerHandshakePacket;
import org.blockserver.network.minecraft.SetHealthPacket;
import org.blockserver.network.minecraft.SetSpawnPositionPacket;
import org.blockserver.network.minecraft.SetTimePacket;
import org.blockserver.network.minecraft.StartGamePacket;
import org.blockserver.network.raknet.ACKPacket;
import org.blockserver.network.raknet.AcknowledgePacket;
import org.blockserver.network.raknet.CustomPacket;
import org.blockserver.network.raknet.InternalPacket;
import org.blockserver.network.raknet.NACKPacket;
import org.blockserver.scheduler.CallbackTask;

public class Player extends Entity implements CommandIssuer, PacketIDs{
	public static long MAX_PING = 15000L;
	private String name;
	private long lastPing, pingMeasure = 0 / 0;
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
	
	private ChunkSender sender;

	public Player(Server server, String ip, int port, short mtu, long clientID){
		super(new Vector3d(0, 128, 0), null);
		lastPing = System.currentTimeMillis(); // should I use nanos instead?
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
		sender = new ChunkSender();
	}
	
	private final class ChunkSender extends Thread{
		public final HashMap<ChunkPosition, IChunk> useChunks = new HashMap<>();
		private final HashMap<Integer, ArrayList<ChunkPosition>> mapOrder = new HashMap<>();
		private final HashMap<ChunkPosition, Boolean> requestChunks = new HashMap<>();
		private final ArrayList<Integer> orders = new ArrayList<>();
		
		public boolean first = true;
		public int lastCX = 0, lastCZ = 0;
		
		@Override
		public final void run(){
			System.out.println("In ChunkSender");
			while (!isInterrupted()){
				int centerX = (int) Math.floor(x) / 16;
				int centerZ = (int) Math.floor(z) / 16;
				try{
					if(centerX == lastCX && centerZ == lastCZ && !first){
						Thread.sleep(100);
						continue;
					}
				}
				catch(InterruptedException e){
					continue;
				}
				System.out.println("Do ChunkSender " + centerX + ", " + centerZ);
				first = false;
				lastCX = centerX; lastCZ = centerZ;
				final int radius = 4; // TODO change to config settings
				mapOrder.clear(); requestChunks.clear(); orders.clear();
				for(int x = -radius; x <= radius; ++x){
					for(int z = -radius; z <= radius; ++z){
						int distance = (x*x) + (z*z);
						int chunkX = x + centerX;
						int chunkZ = z + centerZ;
						if(!mapOrder.containsKey(distance)){
							mapOrder.put(distance, new ArrayList<ChunkPosition>());
						}
						requestChunks.put(ChunkPosition.byDirectIndex(chunkX, chunkZ), true);
						mapOrder.get(distance).add(ChunkPosition.byDirectIndex(chunkX, chunkZ));
						orders.add(distance);
					}
				}
				Collections.sort(orders);
				for(Integer i: orders){
					for(ChunkPosition v: mapOrder.get(i)){
						try{
							if(useChunks.containsKey(v)){
								continue;
							}
							level.getLevelProvider().loadChunk(v, Generator.FLAG_GENERATOR_USAGE);
							useChunks.put(v, level.getLevelProvider().getChunk(v) );
							addToQueue(new FullChunkDataPacket(useChunks.get(v)));
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				//TODO Unload Unused Chunks
				/*
				ChunkPosition[] v2a = requestChunks.keySet().toArray(new Vector2[useChunks.keySet().size()] );
				for( int i = 0; i < v2a.length; i++ ) {
					ChunkPosition v = v2a[i];
					if( !useChunks.containsKey( v2a ) ) {
						level.releaseChunk(v);
						useChunks.remove(v);
					}
				}
				*/
				System.out.println("Do Finish");
				try{
					Thread.sleep(100);
				}
				catch(InterruptedException e){}
			}
			System.out.println("Out ChunkSender");
		}
	}

	public void update(long ticks){
		synchronized(ACKQueue){
			if(this.ACKQueue.size() > 0){
				int[] array = new int[this.ACKQueue.size()];
				int offset = 0;
				for(Integer i: ACKQueue){
					array[offset++] = i;
				}
				ACKPacket pck = new ACKPacket(array);
				pck.encode();
				server.sendPacket(pck.getBuffer(), ip, port);
			}
		}
		synchronized(NACKQueue){
			if(NACKQueue.size() > 0){
				int[] array = new int[NACKQueue.size()];
				int offset = 0;
				for(Integer i: NACKQueue){
					array[offset++] = i;
				}
				NACKPacket pck = new NACKPacket(array);
				pck.encode();
				server.sendPacket(pck.getBuffer(), ip, port);
			}
		}
		if(System.currentTimeMillis() - lastPing >= MAX_PING){
			close(String.format("Ping timeout: %d seconds", (System.currentTimeMillis() - lastPing) / 1000d));
		}
		synchronized(queue){
			if(queue.packets.size() > 0){
                queue.sequenceNumber = sequenceNum++;
				queue.encode();
				server.sendPacket(queue.getBuffer(), ip, port);
				recoveryQueue.put(queue.sequenceNumber, queue);
				queue.packets.clear();
			}
		}
	}

	public void addToQueue(BaseDataPacket pck){
		synchronized(queue){
			pck.encode();
			BlockServer.Debugging.logSentDataPacket(pck, this);
			InternalPacket ipck = new InternalPacket();
			ipck.buffer = pck.getBuffer();
			ipck.reliability = 2;
			ipck.messageIndex = messageIndex++;
			ipck.toBinary();
			if(queue.getLength() + pck.getBuffer().length >= mtuSize){
				queue.sequenceNumber = sequenceNum++;
				queue.encode();
				server.sendPacket(queue.getBuffer(), ip, port);
				queue.packets.clear();
			}
			queue.packets.add(ipck);
		}
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
		synchronized(ACKQueue){
			ACKQueue.add(pck.sequenceNumber);
		}
		for(InternalPacket ipck : pck.packets)
        {
			BlockServer.Debugging.logReceivedInternalPacket(ipck, this);
			switch (ipck.buffer[0]){
				case PING: //PING Packet
					PingPacket pp = new PingPacket(ipck.buffer);
					pp.decode();
					pingMeasure = System.currentTimeMillis() - lastPing;
					lastPing = System.currentTimeMillis();
					PongPacket reply = new PongPacket(pp.pingID);
					addToQueue(reply);
					break;
				case CLIENT_CONNECT: // 0x09. Use the constants class
					ClientConnectPacket ccp = new ClientConnectPacket(ipck.buffer);
					ccp.decode();
					//Send a ServerHandshake packet
					ServerHandshakePacket shp = new ServerHandshakePacket(this.port, ccp.session);
					addToQueue(shp);
					break
				case CLIENT_HANDSHAKE:
					ClientHandShakePacket chs = new ClientHandShakePacket(ipck.buffer);
					chs.decode();
					break;
				case LOGIN:
					LoginPacket lp = new LoginPacket(ipck.buffer);
					server.getLogger().info("Login Packet: %d", ipck.buffer.length);
					lp.decode();
					if(lp.protocol != CURRENT_PROTOCOL || lp.protocol2 != CURRENT_PROTOCOL){
						if(lp.protocol < CURRENT_PROTOCOL || lp.protocol2 < CURRENT_PROTOCOL){
							addToQueue(new LoginStatusPacket(1)); // Client outdated
							close("Wrong Protocol: Client is outdated.");
						}

						if(lp.protocol > CURRENT_PROTOCOL || lp.protocol2 > CURRENT_PROTOCOL){
							addToQueue(new LoginStatusPacket(2)); // Server outdated
							close("Wrong Protocol: Server is outdated.");
						}
					}
					addToQueue(new LoginStatusPacket(0)); // No error with the protocol.
					if(lp.username.length() < 3 || lp.username.length() > 15){
						close("Username is not valid.");
					}
					else{
						name = lp.username;
						server.getLogger().info("%s (%s:%d) logged in with a fake entity ID.", name, ip, port);

						login();
						StartGamePacket sgp = new StartGamePacket(level.getSpawnPos().toVector3(), this, StartGamePacket.GENERATOR_INFINITE, 1, level.getSeed(), 0);
						addToQueue(sgp);
						
						SetTimePacket stp = new SetTimePacket(0);
						addToQueue(stp);
						
						SetSpawnPositionPacket sspp = new SetSpawnPositionPacket( new Vector3(128, 4, 128) );
						addToQueue(sspp);
						
						SetHealthPacket setHealth = new SetHealthPacket((byte) 0x20);
						addToQueue(setHealth);
						
						sender.start();
						
						/*
						sendChatArgs(server.getMOTD());
						server.getChatMgr().broadcast(name + " joined the game.");
						for(Player other: server.getConnectedPlayers()){
							if(!(other.clientID == this.clientID)){
								spawnPlayer(other);
							}
						}
						*/
					}
					break;
				case DISCONNECT:
					disconnect("disconnected by client");
					break;
				case MESSAGE:
					MessagePacket mpk = new MessagePacket(ipck.buffer);
					mpk.decode();
					server.getChatMgr().handleChat(this, mpk.getMessage());
					break;
				case MOVE_PLAYER:
					MovePlayerPacket movePlayer = new MovePlayerPacket( ipck.buffer );
					movePlayer.decode();
					x = movePlayer.x;
					y = movePlayer.y;
					z = movePlayer.z;
					break;
				default:
					server.getLogger().debug("Unsupported packet recived: %02x", ipck.buffer[0]);
                    break;
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
				server.sendPacket(recoveryQueue.get(i).getBuffer(), ip, port);
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
		if(other != this){
			addToQueue(new AddPlayerPacket(other));
		}
	}

	protected void login(){
		List<Player> toKick = new ArrayList<Player>();
		for(Player player: server.getConnectedPlayers()){
			if(player != null && player != this){
				if(player.getName().equalsIgnoreCase(name)){
					toKick.add(player);
				}
			}
		}
		for(Player player: toKick){
			player.close("logging in from another location.");
		}
		PlayerData data = server.getPlayerDatabase().load(this);
		setCoords(data.getCoords());
		level = data.getLevel(); // validated!
		start(server);
	}
	public void close(String reason){
		if(reason != null){
			sendChat(reason);
		}
		addToQueue(new DisconnectPacket());
		disconnect(String.format("kicked (%s)", reason));
		while ( sender.isAlive() ) {
			sender.interrupt();
		}
	}
	protected void disconnect(String reason){
		server.getLogger().info("%s (%s:%d) disconnected: %s.", name, ip, port, reason);
		server.getChatMgr().broadcast(name+" left the game.");
		server.removePlayer(this);
//		server.getPlayerDatabase().save(new PlayerData(level, this, getIName(), getInventory()));
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
	public long getLastPing(){
		return lastPing;
	}
	public long getPingMeasure(){
		return pingMeasure;
	}
	@Override
	public byte getTypeID(){
		return 63;
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
