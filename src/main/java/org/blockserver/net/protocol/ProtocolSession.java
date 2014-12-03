package org.blockserver.net.protocol;

import java.net.SocketAddress;

import org.blockserver.Server;

public interface ProtocolSession{
	public void handlePacket(WrappedPacket pk);
	public SocketAddress getAddress();
	public void sendPacket(byte[] buffer);
	public void closeSession();
	public Server getServer();
}
