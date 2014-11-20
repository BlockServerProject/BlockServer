package org.blockserver.network.protocol;

import java.net.SocketAddress;

import org.blockserver.network.WrappedPacket;

public interface ProtocolSession{
	public void handlePacket(WrappedPacket pk);
	public SocketAddress getAddress();
	public void sendPacket(byte[] buffer);
	public void closeSession();
}
