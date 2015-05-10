package org.blockserver.net.protocol.pe;


import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.raknet.ConnectionReply1Packet;
import org.blockserver.net.protocol.pe.raknet.ConnectionRequest1Packet;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	//private Map<Integer, CustomPacket> packetQueue = new HashMap<>();

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
				server.getLogger().buffer("Got Request 2: ", pk.bb().array(), "");
				break;

			default:
				if(pid >= RAKNET_CUSTOM_PACKET_MIN && pid <= RAKNET_CUSTOM_PACKET_MAX){
					//Custom Packet
				} else {
					server.getLogger().buffer("Unknown Packet: ", pk.bb().array(), "");
				}
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
