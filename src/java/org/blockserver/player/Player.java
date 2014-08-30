package org.blockserver.player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.Entity;
import org.blockserver.entity.EntityType;
import org.blockserver.math.Vector3;
import org.blockserver.network.minecraft.BaseDataPacket;
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
import org.blockserver.scheduler.CallBackTask;

public class Player extends Entity{
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

	public String getIP(){
		return ip;
	}

	public Player(String ip, int port, short mtu, long clientID){
		super(0, 0, 0, null);
		this.ip = ip.replace("/", "");
		this.port = port;
		mtuSize = mtu;
		this.clientID = clientID;

		lastSequenceNum = sequenceNum = messageIndex = 0;

		server = Server.getInstance();
		queue = new CustomPacket();
		ACKQueue = new ArrayList<Integer>();
		NACKQueue = new ArrayList<Integer>();
		recoveryQueue = new HashMap<Integer, CustomPacket>();

		try{
			server.getScheduler().addTask(new CallBackTask(this, "update", 10, true)); // do this with the entity context?
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void login(){
		server.getPlayerDatabase().load(getIName());
	}

	public void update(int ticks){
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

					if(lp.username.length() < 3 || lp.username.length() > 15){
						close("Username is not valid.");
					}
					else{
						name = lp.username;
						server.getLogger().info(name+"("+ip+":"+port+") logged in with a fake entity ID.");
	
						//login();
	
						//Once we get World gen up, uncomment this:
						/*
						StartGamePacket sgp = new StartGamePacket(server.getDefaultLevel(), this.entityID);
						sgp.encode();
						this.addToQueue(sgp);
						
						*/
						
						//START Fake StartGamePacket
						StartGamePacket sgp = new StartGamePacket(new Vector3(100, 2, 100), 1, 100, 1);
						sgp.encode();
						addToQueue(sgp);
						//END Fake StartGamePacket
					}
					
					break;
				
				default:
					//server.getLogger().info("Internal Packet Received packet: %02x", ipck.buffer[0]);
					server.getLogger().debug("Unsupported packet recived: %02x", ipck.buffer[0]);
			}
		}
	}

	public void handleAcknowledgePackets(AcknowledgePacket pck){ // Ack and Nack
		pck.decode();
		if(pck instanceof ACKPacket){ // When we receive a ACK Packet then
			for(int i: pck.sequenceNumbers){
				server.getLogger().info("ACK Packet Received Seq: %d", i);
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

	public void sendMessage(String msg){
		addToQueue(new MessagePacket(msg)); // be aware of the message-too-long exception
	}

	public void close(String reason){
		sendMessage(reason);
		addToQueue(new Disconnect());
	}

	public InetAddress getAddress() throws UnknownHostException{
		return InetAddress.getByName(ip);
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

	public long getClientID() {
		return clientID;
	}
}
