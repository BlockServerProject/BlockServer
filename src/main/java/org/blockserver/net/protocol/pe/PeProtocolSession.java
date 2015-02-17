package org.blockserver.net.protocol.pe;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.*;

import org.blockserver.Server;
import org.blockserver.api.API;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.internal.response.PingResponse;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.login.*;
import org.blockserver.net.protocol.pe.play.EncapsulatedPlayPacket;
import org.blockserver.net.protocol.pe.sub.gen.*;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.PeSubprotocolMgr;
import org.blockserver.net.protocol.pe.sub.gen.ping.McpePongPacket;
import org.blockserver.player.Player;
import org.blockserver.player.PlayerLoginInfo;
import org.blockserver.ticker.CallableTask;
import org.blockserver.utils.Position;

public class PeProtocolSession implements ProtocolSession, PeProtocolConst{
	private ProtocolManager mgr;
	private NetworkBridge bridge;
	private SocketAddress addr;
	private PeProtocol pocket;
	private PeSubprotocolMgr subprotocols;
	private long clientId;
	private short mtu;
	private PeSubprotocol subprot = null;
	private Player player = null;
	
	private int lastSequenceNum = 0;
	private int currentSequenceNum = 0;
	private int currentMessageIndex = 0;
	private final RaknetSentCustomPacket currentQueue;
	private final List<Integer> ACKQueue;
	private final List<Integer> NACKQueue;
	private Map<Integer, RaknetSentCustomPacket> recoveryQueue;

	
	public PeProtocolSession(ProtocolManager mgr, NetworkBridge bridge, SocketAddress addr, PeProtocol pocket){
		this.mgr = mgr;
		this.bridge = bridge;
		this.addr = addr;
		this.pocket = pocket;
		subprotocols = pocket.getSubprotocols();
		try{
			getServer().getTicker().addRepeatingTask(new CallableTask(this, "update"), 10);
		}
		catch(NoSuchMethodException e){
			e.printStackTrace();
		}
		ACKQueue = new ArrayList<>();
		NACKQueue = new ArrayList<>();
		recoveryQueue = new HashMap<>();
		currentQueue = new RaknetSentCustomPacket();
		
		getServer().getLogger().debug("Started session from %s", addr.toString());
	}
	@Override
	public SocketAddress getAddress(){
		return addr;
	}
	
	public void addToQueue(EncapsulatedLoginPacket lp){
		addToQueue(lp.getBuffer().array(), 2);
	}
	
	public void addToQueue(byte[] buffer){
		addToQueue(buffer, 2);
	}
	
	public void addToQueue(EncapsulatedPlayPacket pp){
		addToQueue(pp.getBuffer().array(), 2);
	}
	
	public synchronized void addToQueue(byte[] buffer, int reliability){ //Use this to send encapsulatedPacket
		if((currentQueue.getLength() + buffer.length) >= mtu){
			currentQueue.seqNumber = currentSequenceNum++;
			currentQueue.send(bridge, addr);
			recoveryQueue.put(currentQueue.seqNumber, currentQueue);
			currentQueue.packets.clear();
		}
		RaknetSentCustomPacket.SentEncapsulatedPacket pk = new RaknetSentCustomPacket.SentEncapsulatedPacket(buffer, (byte) reliability);
		pk.messsageIndex = currentMessageIndex++;
		currentQueue.packets.add(pk);
	}

	public void sendResponse(InternalResponse response){
		if(response instanceof PingResponse){
			McpePongPacket pongPacket = new McpePongPacket(((PingResponse) response).pingId);
			addToQueue(pongPacket.encode());
			getServer().getLogger().debug("Ping response added to queue.");
		}
		else{
			throw new UnsupportedOperationException("Unknown/Unsupported InternalResponse.");
		}
	}
	
	public synchronized void update(){
		synchronized(ACKQueue){
			if(ACKQueue.size() > 0){
				int[] numbers = new int[ACKQueue.size()];
				int offset = 0;
				for(Integer i: ACKQueue){
					numbers[offset++] = i;
				}
				ACKPacket ack = new ACKPacket(numbers);
				ack.encode();
				sendPacket(ack.getBuffer());
				ACKQueue.clear();
			}
		}
		synchronized(NACKQueue){
			if(NACKQueue.size() > 0){
				int[] numbers = new int[NACKQueue.size()];
				int offset = 0;
				for(Integer i: NACKQueue){
					numbers[offset++] = i;
				}
				NACKPacket nack = new NACKPacket(numbers);
				nack.encode();
				sendPacket(nack.getBuffer());
				NACKQueue.clear();
			}
		}
		synchronized(currentQueue){
			if(currentQueue.packets.size() > 0){
				currentQueue.seqNumber = currentSequenceNum++;
				for(int i = 0; i < currentQueue.packets.size(); i++){
					boolean approved = getServer().getAPI().onDataPacketSent(player, new API.Argument<RaknetSentCustomPacket.SentEncapsulatedPacket>(currentQueue.packets.get(i)));
					if(!approved){
						currentQueue.packets.remove(i);
					}
				}
				if(currentQueue.packets.size() > 0) {
					currentQueue.send(bridge, getAddress());
					recoveryQueue.put(currentQueue.seqNumber, currentQueue);
					currentQueue.packets.clear();
				}
			}
		}
	}

	@Override
	public void handlePacket(WrappedPacket pk){
		ByteBuffer bb = pk.bb();
		byte pid = bb.get();
		debug("Handling packet (PID " + pid + ")");
		if(pid <= RAKNET_CUSTOM_PACKET_MAX){ // {@code pid} starts from 0x80, which is the smallest number for a {@code byte} in Java
			handleCustomPacket(pid, bb);
		}
		else{
			switch(pid){
				case RAKNET_BROADCAST_PING_1:
				case RAKNET_BROADCAST_PING_2:
					replyToBroadcastPing(bb);
					break;
				case RAKNET_OPEN_CONNECTION_REQUEST_1:
					replyToRequest1(bb);
					break;
				case RAKNET_OPEN_CONNECTION_REQUEST_2:
					replyToRequest2(bb);
					break;
			}
		}
	}

	private void replyToBroadcastPing(ByteBuffer bb){
		RaknetUnconnectedPing ping = new RaknetUnconnectedPing(bb);
		RaknetUnconnectedPong pong = new RaknetUnconnectedPong(ping.pingId, SERVER_ID, ping.magic, getServer().getServerName());
		sendPacket(pong.getBuffer());
	}

	private void replyToRequest1(ByteBuffer bb){
		RaknetOpenConnectionRequest1 req1 = new RaknetOpenConnectionRequest1(bb);
		if(req1.raknetVersion != RAKNET_PROTOCOL_VERSION){
			sendIncompatibility(req1.magic);
		}
		mtu = (short) bb.capacity();
		RaknetOpenConnectionReply1 rep1 = new RaknetOpenConnectionReply1(req1.magic, req1.payloadLength + 18);
		sendPacket(rep1.getBuffer());
		getServer().getLogger().debug("Replied to request 1");
	}
	private void sendIncompatibility(byte[] magic){
		RaknetIncompatibleProtocolVersion ipv = new RaknetIncompatibleProtocolVersion(magic, SERVER_ID);
		sendPacket(ipv.getBuffer());
		closeSession("Protocol version not supported by server.");
	}

	private void replyToRequest2(ByteBuffer bb){
		debug("Replying to request 2.");
		RaknetOpenConnectionRequest2 req2 = new RaknetOpenConnectionRequest2(bb);
		debug("MTU is: "+mtu);
		RaknetOpenConnectionReply2 rep2 = new RaknetOpenConnectionReply2(req2.magic, req2.serverPort, mtu);
		sendPacket(rep2.getBuffer());
		getServer().getLogger().debug("Replied to request 2");
	}

	@SuppressWarnings("UnusedParameters")
	private void handleCustomPacket(final byte pid, final ByteBuffer bb){
//		AntiSpam.act(new Runnable(){
//			@Override
//			public void run(){
//				byte[] buffer = new byte[bb.remaining()];
//				int start = bb.position();
//				int end = start + bb.remaining();
//				for(int i = start; i < end; i++){
//					buffer[i - start] = bb.get(i);
//				}
//				StringBuilder sb = new StringBuilder(Integer.toHexString(pid));
//				for(byte b: buffer){
//					String s = Integer.toHexString(b);
//					while(s.length() < 2){
//						s = "0" + s;
//					}
//					sb.append(s);
//				}
//				System.out.println("Full encapsulated packet buffer: " + sb.toString());
//			}
//		}, "PocketProtocolSession custom", 2000);
		RaknetReceivedCustomPacket cp = new RaknetReceivedCustomPacket(bb);
		acknowledge(cp);
		if(cp.seqNumber - lastSequenceNum == 1){
			lastSequenceNum = cp.seqNumber;
		} else {
			synchronized (NACKQueue) {
				for(int i = lastSequenceNum; i < cp.seqNumber; ++i){
					NACKQueue.add(i);
				}
			}
		}
		for(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket pk: cp.packets){
			handleDataPacket(pk);
		}
	}
	private void handleDataPacket(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket pk){
		if((pk.buffer[0] <= 0x13 && pk.buffer[0] >= 0x09) || pk.buffer[0] == (byte) 0x82){ //MCPE Data Login packet range + Login Packet(0x82)
			handleDataLogin(pk);
		} else if(pk.buffer[0] == MC_LOGIN_PACKET){
			handleDataLogin(pk);
		}
//		if(pk.buffer[0] == MC_LOGIN_PACKET){
//			McpeLoginPacket lp = new McpeLoginPacket(ByteBuffer.wrap(pk.buffer));
//			subprot = subprotocols.findProtocol(lp.protocol1, lp.protocol2);
//			if(subprot == null){
//				String version = Integer.toString(lp.protocol1);
//				if(lp.protocol2 != lp.protocol1){
//					version += ", #" + Integer.toString(lp.protocol2);
//				}
//				disconnect("Unsupported protocol version(s) #" + version);
//			}
//			else{
//				player = getServer().newSession(this);
//			}
//		}
		else if(subprot == null){
			//noinspection UnnecessaryReturnStatement
			getServer().getAPI().onDataPacketReceived(player, new API.Argument<RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket>(pk), new API.Argument<Boolean>(false));
			return; // TODO handle
		}
		else{
			API.Argument<Boolean> handled = new API.Argument<>(false);
			boolean handle = getServer().getAPI().onDataPacketReceived(player, new API.Argument<RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket>(pk), handled);
			if((!handled.value) && handle == true) {
				if (player != null) {
					subprot.readDataPacket(pk, player);
				} else {
					//TODO
				}
			}
		}
	}
	private void handleDataLogin(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket cp){
		ByteBuffer bb = ByteBuffer.wrap(cp.buffer);
		byte pid = bb.get();
		debug(String.format("Handling Login Packet %X", pid));
		switch(pid){
			case MC_CLIENT_CONNECT:
				McpeClientConnectPacket pk = new McpeClientConnectPacket(bb);
				long session = pk.session;
				
				ServerHandshakePacket shp = new ServerHandshakePacket(session, (short) bridge.getServer().getPort());
				shp.encode();
				addToQueue(shp);
				System.out.println("ServerHandshake: OUT");
				break;
				
			case MC_CLIENT_HANDSHAKE:
				ClientHandshakePacket chp = new ClientHandshakePacket(bb);
				//chp.decode();
				break;
				
			case MC_LOGIN_PACKET:
				McpeLoginPacket lp = new McpeLoginPacket(bb);
				lp.decode();
				//System.out.println("Login.");
				login(lp);
				sendInitalPackets();

				break;
			default:
				getServer().getLogger().warning("Unknown/Unsupported Login Packet recieved. Dropped "+cp.buffer.length+" bytes.");
				break;
		}
	}
	public void handleAcknowledgePackets(AcknowledgePacket ap){
		ap.decode();
		if(ap instanceof ACKPacket){
			for(int i: ap.sequenceNumbers){
				getServer().getLogger().info("ACK packet recieved #"+i);
				recoveryQueue.remove(i);
			}
		}
		else if(ap instanceof NACKPacket){
			for(int i: ap.sequenceNumbers){
				getServer().getLogger().warning("NACK packet recieved #"+i);
				recoveryQueue.get(i).send(bridge, getAddress());
			}
		}
	}
	private synchronized void acknowledge(RaknetReceivedCustomPacket cp){
		ACKQueue.add(cp.seqNumber);
		debug("Added Acknowledge packet #"+cp.seqNumber);
	}

	private void sendInitalPackets(){
		//TODO: Send set time, adventure settings, and spawn position
		McpeStartGamePacket sgp = new McpeStartGamePacket();
		sgp.eid = player.getEntityID();
		sgp.gamemode = player.getGamemode();
		sgp.generator = 0; //INFINITE
		sgp.seed = 0; //Dummy
		sgp.spawnX = (int) getServer().getSpawnPosition().getX();
		sgp.spawnY = (int) getServer().getSpawnPosition().getY();
		sgp.spawnZ = (int) getServer().getSpawnPosition().getZ();

		sgp.x = (int) player.getLocation().getX();
		sgp.y = (int) player.getLocation().getY();
		sgp.z = (int) player.getLocation().getZ();

		addToQueue(sgp.encode());
		System.out.println("Start Game out.");

		McpeSetTimePacket setTimePacket = new McpeSetTimePacket(0); //Dummy
		addToQueue(setTimePacket.encode());

		McpeSpawnPositionPacket spawnPositionPacket = new McpeSpawnPositionPacket(player.getLocation());
		addToQueue(spawnPositionPacket.encode());

		/*
		McpeSetDifficultyPacket difficultyPacket = new McpeSetDifficultyPacket(1); //Dummy
		addToQueue(difficultyPacket.encode());
		*/

		McpeSetHealthPacket setHealthPacket = new McpeSetHealthPacket(20);
		addToQueue(setHealthPacket.encode());

		PeChunkSender sender = new PeChunkSender(this);
		sender.start();
	}
	
	private void login(McpeLoginPacket lp){
		//TODO
		if(lp.protocol1 == lp.protocol2){
			LoginStatusPacket lsp = new LoginStatusPacket();
			if(subprotocols.findProtocol(lp.protocol1) != null){ //Protocol is supported
				subprot = subprotocols.findProtocol(lp.protocol1);

				PlayerLoginInfo info = new PlayerLoginInfo();
				info.username = lp.username;
				info.entityID = getServer().getNextEntityID();

				this.player = getServer().newSession(this, info);
				Position loc = player.getLocation();

				lsp.status = 0;
				lsp.encode();
				addToQueue(lsp);

				getServer().getLogger().info(player.getUsername()+" logged in at (x: "+loc.getX()+",y: "+loc.getY()+",z: "+loc.getZ()+") with entity ID: "+player.getEntityID());
			} else {
				lsp.status = 2;
				lsp.encode();
				addToQueue(lsp);

				closeSession("Outdated!");
			}
		} else {
			//TODO
			System.out.println("Protocol 1: "+lp.protocol1+", 2: "+lp.protocol2);
		}
	}
	
	public short getMtu(){
		return mtu;
	}
	public long getClientId(){
		return clientId;
	}
	@Override
	public Server getServer(){
		return bridge.getServer();
	}
	
	@Override
	public void sendPacket(byte[] buffer){
		bridge.send(buffer, getAddress());
	}
	@Override
	public void closeSession(String reason){
		mgr.closeSession(getAddress());
		disconnect(reason);
		getServer().getLogger().info(player.getUsername()+"["+getAddress().toString()+"]"+" was disconnected: "+reason);
	}
	public void disconnect(String reason){
		McpeDisconnectPacket disconnect = new McpeDisconnectPacket(reason);
		addToQueue(disconnect.encode());
	}
	public PeProtocol getPeProtocol(){
		return pocket;
	}
	public Player getPlayer(){
		return player;
	}
	private void debug(String msg){
		getServer().getLogger().debug("[PocketProtocolSession %s] %s", getAddress().toString(),
				msg);
	}
	private void buffer(String front, byte[] buffer, String end){
		getServer().getLogger().buffer("[PocketProtocolSession " + getAddress().toString()
				+ "] " + front, buffer, end);
	}
}
