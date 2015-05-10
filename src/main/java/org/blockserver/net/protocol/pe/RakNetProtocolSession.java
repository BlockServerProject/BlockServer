package org.blockserver.net.protocol.pe;


import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;

import java.net.SocketAddress;


public class RakNetProtocolSession implements ProtocolSession, PeProtocolConst{

	public RakNetProtocolSession(ProtocolManager protocols, NetworkBridge bridge, SocketAddress address, PeProtocol peProtocol) {

	}

	@Override
	public void handlePacket(WrappedPacket pk) {

	}

	@Override
	public SocketAddress getAddress() {
		return null;
	}

	@Override
	public void sendPacket(byte[] buffer) {

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
