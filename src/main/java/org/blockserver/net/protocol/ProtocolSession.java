package org.blockserver.net.protocol;

import java.net.SocketAddress;

import org.blockserver.Server;
import org.blockserver.net.internal.response.InternalResponse;

public interface ProtocolSession{
	public void handlePacket(WrappedPacket pk);
	public SocketAddress getAddress();
	public void sendPacket(byte[] buffer);
	public void sendResponse(InternalResponse response);
	public void closeSession(String reason);
	public Server getServer();
}
