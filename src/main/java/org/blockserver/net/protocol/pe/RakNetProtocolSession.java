package org.blockserver.net.protocol.pe;


import org.blockserver.Server;
import org.blockserver.api.event.net.ResponseSendNativeEvent;
import org.blockserver.api.event.net.protocol.pe.PEDataPacketSendNativeEvent;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.internal.request.PingRequest;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.internal.response.PingResponse;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.raknet.*;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.login.ClientConnect;
import org.blockserver.net.protocol.pe.sub.login.ServerHandshake;
import org.blockserver.net.protocol.pe.sub.login.StartGamePacket;
import org.blockserver.net.protocol.pe.sub.v20.LoginPacketV20;
import org.blockserver.net.protocol.pe.sub.v20.LoginStatusPacket;
import org.blockserver.net.protocol.pe.sub.v20.PingPacket;
import org.blockserver.net.protocol.pe.sub.v20.PongPacket;
import org.blockserver.net.protocol.pe.sub.v27.BatchPacket;
import org.blockserver.net.protocol.pe.sub.v27.DisconnectPacket;
import org.blockserver.net.protocol.pe.sub.v27.LoginPacketV27;
import org.blockserver.net.protocol.pe.sub.v27.PlayStatusPacket;
import org.blockserver.player.Player;
import org.blockserver.player.PlayerLoginInfo;
import org.blockserver.ticker.CallableTask;
import org.blockserver.utils.Utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Protocol Session implementation for MCPE RakNet.
 */
public class RakNetProtocolSession implements ProtocolSession, PeProtocolConst{
	private ProtocolManager protoMgr;
	private NetworkBridge bridge;
	private SocketAddress clientAddress;
	private PeProtocol protocol;
	private PeSubprotocol subprotocol = null;
	private Server server;
	private Player player;

	private int lastSeqNum = -1;
	private int nextSeqNum = -1;
	private int nextMessageIndex = 0;
	private int MTU;

	private boolean loggedIn = false;

	private List<Integer> ACKQueue = new ArrayList<>();
	private List<Integer> NACKQueue = new ArrayList<>();
	private Map<Integer, CustomPacket> recoveryQueue = new HashMap<>();
	private CustomPacket currentQueue = new CustomPacket();

	public RakNetProtocolSession(ProtocolManager protocols, NetworkBridge bridge, SocketAddress address, PeProtocol peProtocol){
		protoMgr = protocols;
		this.bridge = bridge;
		clientAddress = address;
		protocol = peProtocol;

		server = bridge.getServer();

		try {
			server.getTicker().addRepeatingTask(new CallableTask(this, "updateQueues"), 10);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		server.getLogger().debug("Accepted new RakNet session from "+address.toString());

	}

	/**
	 * INTERNAL METHOD! Do not call!
	 */
	public void updateQueues(){
		synchronized (ACKQueue){
			if(!ACKQueue.isEmpty()){
				int[] numbers = new int[ACKQueue.size()];
				for(int i = 0; i < numbers.length; i++){
					numbers[i] = ACKQueue.get(i);
				}
				ACKPacket ack = new ACKPacket();
				ack.sequenceNumbers = numbers;
				sendPacket(ack.encode());

				ACKQueue.clear();
			}
		}
		synchronized (NACKQueue){
			if(!NACKQueue.isEmpty()){
				int[] numbers = new int[NACKQueue.size()];
				for(int i = 0; i < numbers.length; i++){
					numbers[i] = NACKQueue.get(i);
				}
				NACKPacket nack = new NACKPacket();
				nack.sequenceNumbers = numbers;
				sendPacket(nack.encode());

				NACKQueue.clear();
			}
		}
		synchronized (currentQueue){
			if(!currentQueue.packets.isEmpty()){
				currentQueue.sequenceNumber = nextSeqNum;
				nextSeqNum++;
				PEDataPacketSendNativeEvent evt = new PEDataPacketSendNativeEvent(currentQueue, this);
				if(server.getAPI().handleEvent(evt)){
					sendPacket(evt.getPacket().encode());
					recoveryQueue.put(evt.getPacket().sequenceNumber, evt.getPacket());
				}
				currentQueue.packets.clear();
			}
		}
	}

	public synchronized void addToQueue(PeDataPacket dp){
		CustomPacket.InternalPacket ip = new CustomPacket.InternalPacket();
		if(dp.getChannel() != NetworkChannel.CHANNEL_NONE){
			ip.reliability = 3;
			ip.orderChannel = dp.getChannel().getAsByte();
			ip.orderIndex = 0;
		} else {
			ip.reliability = 2;
		}
		ip.buffer = dp.encode();
		ip.messageIndex = nextMessageIndex++;

		synchronized (currentQueue) {
			if (PacketAssembler.checkIfSplitNeeded(currentQueue, ip, this)) {
				//TODO
			} else {
				currentQueue.packets.add(ip);
			}
		}
	}

	public synchronized void addToQueue(byte[] buffer, NetworkChannel channel, int reliability){
		CustomPacket.InternalPacket ip = new CustomPacket.InternalPacket();
		ip.reliability = (byte) reliability;
		ip.buffer = buffer;
		ip.orderChannel = channel.getAsByte();
		ip.orderIndex = 0;
		if(reliability == 0x02 || reliability == 0x03 || reliability == 0x04 || reliability == 0x06 || reliability == 0x07){
			ip.messageIndex = nextMessageIndex++;
		}
		synchronized (currentQueue) {
			if (PacketAssembler.checkIfSplitNeeded(currentQueue, ip, this)) {
				//TODO
			} else {
				currentQueue.packets.add(ip);
			}
		}
	}

	@Override
	public void handlePacket(WrappedPacket pk) {
		byte pid = pk.bb().get();
		switch (pid){
			case RAKNET_OPEN_CONNECTION_REQUEST_1:
				ConnectionRequest1Packet request1 = new ConnectionRequest1Packet();
				request1.decode(pk.bb());

				MTU = pk.bb().capacity();

				ConnectionReply1Packet reply1 = new ConnectionReply1Packet();
				reply1.mtuSize = (short) pk.bb().capacity();
				sendPacket(reply1.encode());
				break;

			case RAKNET_OPEN_CONNECTION_REQUEST_2:
				ConnectionRequest2Packet request2 = new ConnectionRequest2Packet();
				request2.decode(pk.bb().array());

				ConnectionReply2Packet reply2 = new ConnectionReply2Packet();
				reply2.clientUdpPort = (short) Utils.getPortFromSocketAddress(pk.getAddress());
				reply2.mtuSize = request2.mtuSize;
				sendPacket(reply2.encode());
				break;

			case RAKNET_ACK:
				ACKPacket ack = new ACKPacket();
				ack.decode(pk.bb().array());
				for(int num : ack.sequenceNumbers){
					if(recoveryQueue.containsKey(num)){
						recoveryQueue.remove(num);
					}
				}
				break;

			case RAKNET_NACK:
				NACKPacket nack = new NACKPacket();
				nack.decode(pk.bb().array());
				for(int num: nack.sequenceNumbers){
					if(recoveryQueue.containsKey(num)){
						//TODO: Fire send packet event?
						sendPacket(recoveryQueue.get(num).encode());
					} else {
						server.getLogger().warning("Received NACK for sequence "+num+" but not found in recoveryQueue.");
					}
				}
				break;

			default:
				if(pid >= RAKNET_CUSTOM_PACKET_MIN && pid <= RAKNET_CUSTOM_PACKET_MAX){
					handleCustomPacket(pk.bb().array());
				} else {
					server.getLogger().buffer("Unknown Packet: ", pk.bb().array(), "");
				}
				break;
		}
	}

	private void handleCustomPacket(byte[] buffer) {
		CustomPacket cp = new CustomPacket();
		cp.decode(buffer);
		synchronized (ACKQueue) {
			ACKQueue.add(cp.sequenceNumber);
		}
		synchronized ((Integer) lastSeqNum) {
			if (cp.sequenceNumber - lastSeqNum == 1) {
				lastSeqNum = cp.sequenceNumber;
			} else {
				int diff = cp.sequenceNumber - lastSeqNum;
				if(diff < 1){ //They must of had not received one of our packets.
					for(int i = cp.sequenceNumber; i < lastSeqNum; i++){
						synchronized (recoveryQueue){
							if(recoveryQueue.keySet().contains(i)){
								sendPacket(recoveryQueue.get(i).encode());
							} else {
								server.getLogger().debug("Client is missing one of our packets, but was not in recoveryQueue: "+i);
							}
						}
					}
				} else if(diff > 1){ //We must of not received one of their packets
					for(int i = lastSeqNum; i < cp.sequenceNumber; i++){
						synchronized (NACKQueue){
							NACKQueue.add(i);
						}
					}
				}
			}
		}

		if(PacketAssembler.checkForSplitPackets(cp)){
			List<CustomPacket.InternalPacket> splitPackets = PacketAssembler.getSplitPackets(cp);
			cp.packets.removeAll(splitPackets);
			List<PacketAssembler.AssembledPacket> assembledPackets = PacketAssembler.assemblePackets(splitPackets);

			for(CustomPacket.InternalPacket ip : cp.packets){
				handleDataPacket(ip.buffer);
			}
			for(PacketAssembler.AssembledPacket assembledPacket : assembledPackets){
				handleDataPacket(assembledPacket.getBuffer());
			}
		} else {
			for(CustomPacket.InternalPacket ip : cp.packets){
				handleDataPacket(ip.buffer);
			}
		}
	}

	private void handleDataPacket(byte[] buffer) {
		byte pid = buffer[0];
		switch (pid){
			case MC_CLIENT_CONNECT:
				ClientConnect cc = new ClientConnect();
				cc.decode(buffer);

				ServerHandshake sh = new ServerHandshake();
				sh.serverPort = (short) server.getPort();
				sh.sessionID = cc.sessionID;
				addToQueue(sh);

				break;

			case MC_BATCH:
				BatchPacket batch = new BatchPacket();
				batch.decode(buffer);
				try {
					processBatch(batch);
				} catch (DataFormatException e) {
					server.getLogger().error("DataFormatException while processing batch packet: "+e.getMessage());
					e.printStackTrace();
				}
				break;

			case MC_LOGIN_PACKET:
				if(loggedIn){
					break;
				}
				if(buffer.length > 150){ //Looks like we have ourselves a 0.11.0+ client (skin)
					LoginPacketV27 lp = new LoginPacketV27();
					lp.decode(buffer);
					PeSubprotocol sub = protocol.getSubprotocols().findProtocol(lp.protocol); //Search for a handler for their protocol
					if(sub != null){
						subprotocol = sub;
						PlayerLoginInfo info = new PlayerLoginInfo();
						info.entityID = server.getNextEntityID();
						info.username = lp.username;
						info.skin = lp.skin;

						player = server.newSession(this, info);

						PlayStatusPacket psp = new PlayStatusPacket();
						psp.status = PlayStatusPacket.LOGIN_SUCCESS;
						psp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
						addToQueue(psp);
						sendInitalPackets();

					} else { //Sadly, we don't support this protocol.
						PlayStatusPacket psp = new PlayStatusPacket();
						psp.status = PlayStatusPacket.LOGIN_FAILED_SERVER;
						psp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
						addToQueue(psp);

						closeSession("login-fail-27-"+Integer.toString(lp.protocol));
						protoMgr.closeSession(getAddress());
					}
				} else { //Under protocol 21
					LoginPacketV20 lp = new LoginPacketV20();
					lp.decode(buffer);

					PeSubprotocol sub = protocol.getSubprotocols().findProtocol(lp.protocol); //Search for a handler for their protocol
					if(sub != null){
						subprotocol = sub;

						PlayerLoginInfo info = new PlayerLoginInfo();
						info.entityID = server.getNextEntityID();
						info.username = lp.username;

						player = server.newSession(this, info);

						LoginStatusPacket lsp = new LoginStatusPacket();
						lsp.status = 0;
						lsp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
						addToQueue(lsp);

						sendInitalPackets();
					} else {
						LoginStatusPacket lsp = new LoginStatusPacket();
						lsp.status = 1;
						lsp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
						addToQueue(lsp);

						closeSession("login-fail-21-"+Integer.toString(lp.protocol));
						protoMgr.closeSession(getAddress());
					}
				}
				break;

			case MC_PLAY_PING:
				if(subprotocol == null){
					PingPacket ping = new PingPacket();
					ping.decode(buffer);

					PongPacket pong = new PongPacket();
					pong.pingID = ping.pingID;
					addToQueue(pong);
					break;
				}

			default:
				if(subprotocol != null) {
					subprotocol.readDataPacket(buffer, player);
				}
				server.getLogger().buffer("Got Unknown Packet: ", buffer, " END.");
				break;
		}
	}

	private void sendInitalPackets() {
		StartGamePacket sgp = new StartGamePacket();
		sgp.seed = -1;
		sgp.x = (float) player.getLocation().getX();
		sgp.y = (float) player.getLocation().getY();
		sgp.z = (float) player.getLocation().getZ();
		sgp.spawnX = (int) server.getSpawnPosition().getX();
		sgp.spawnY = (int) server.getSpawnPosition().getY();
		sgp.spawnZ = (int) server.getSpawnPosition().getZ();
		sgp.generator = 1;
		sgp.gamemode = player.getGamemode() & 0x01;
		sgp.entityID = player.getEntityID();
		sgp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
		addToQueue(sgp);


		PlayStatusPacket psp = new PlayStatusPacket();
		psp.status = PlayStatusPacket.PLAYER_SPAWN;
		psp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
		addToQueue(psp);
	}

	private void processBatch(BatchPacket batch) throws DataFormatException {
		Inflater decompress = new Inflater();
		decompress.setInput(batch.payload, 0, batch.payload.length);
		byte[] decompressed = new byte[1024 * 1024]; //From PocketMine
		int len = decompress.inflate(decompressed);
		decompress.end();

		byte[] buffer = Arrays.copyOf(decompressed, len);
		handleDataPacket(buffer);
	}

	@Override
	public SocketAddress getAddress() {
		return clientAddress;
	}

	@Override
	public void sendPacket(byte[] buffer) {
		bridge.send(buffer, clientAddress);
	}

	@Override
	public void sendResponse(InternalResponse response) {
		ResponseSendNativeEvent evt = new ResponseSendNativeEvent(player, response);
		if(server.getAPI().handleEvent(evt)){
			if(evt.getResponse() instanceof PingResponse){
				PongPacket pong = new PongPacket();
				pong.pingID = ((PingResponse) evt.getResponse()).pingId;
				addToQueue(pong);
			}
		}
	}

	@Override
	public void closeSession(String reason) {
		if(reason.startsWith("login-fail-27")) {
			DisconnectPacket dp = new DisconnectPacket();
			dp.reason = "";
			dp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
			addToQueue(dp);
			String[] broken = reason.split("-");
			String protocol = broken[3];
			server.getLogger().info("[" + getAddress().toString() + "] was disconnected: invalid protocol " + protocol);
		} else if(reason.startsWith("login-fail-21")) {
			addToQueue(new byte[] {MC_DISCONNECT}, NetworkChannel.CHANNEL_PRIORITY, 3);

			String[] broken = reason.split("-");
			String protocol = broken[3];
			server.getLogger().info("[" + getAddress().toString() + "] was disconnected: invalid protocol " + protocol);
		} else {
			DisconnectPacket dp = new DisconnectPacket();
			dp.reason = reason;
			dp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
			addToQueue(dp);
			if(player != null) {
				server.getLogger().info(player.getUsername() + "[" + getAddress().toString() + "] was disconnected: " + reason);
			}
		}
	}

	@Override
	public Server getServer() {
		return server;
	}

	public int getMTU() {
		return MTU;
	}
}
