package org.blockserver.net.protocol.pe;


import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.raknet.*;
import org.blockserver.utils.Utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Protocol Session implementation for MCPE RakNet.
 */
public class RakNetProtocolSession implements ProtocolSession, PeProtocolConst{
	private ProtocolManager protoMgr;
	private NetworkBridge bridge;
	private SocketAddress clientAddress;
	private PeProtocol protocol;
	private Server server;

	private int lastSeqNum = -1;
	private int currentSeqNum = 0;
	private int nextMessageIndex = -1;

	private List<Integer> ACKQueue = new ArrayList<>();
	private List<Integer> NACKQueue = new ArrayList<>();
	private Map<Integer, CustomPacket> recoveryQueue = new HashMap<>();

	public RakNetProtocolSession(ProtocolManager protocols, NetworkBridge bridge, SocketAddress address, PeProtocol peProtocol) {
		protoMgr = protocols;
		this.bridge = bridge;
		clientAddress = address;
		protocol = peProtocol;

		server = bridge.getServer();

		server.getLogger().debug("Accepted new RakNet session from "+address.toString());

	}

	@Override
	public void handlePacket(WrappedPacket pk) {
		byte pid = pk.bb().get();
		switch (pid){
			case RAKNET_OPEN_CONNECTION_REQUEST_1:
				ConnectionRequest1Packet request1 = new ConnectionRequest1Packet();
				request1.decode(pk.bb());

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

			default:
				server.getLogger().buffer("Got Unkown Packet: ", buffer, " END.");
				break;
		}
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

	}

	@Override
	public void closeSession(String reason) {

	}

	@Override
	public Server getServer() {
		return null;
	}
}
