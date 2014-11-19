package org.blockserver.network.protocol;

import java.net.SocketAddress;

public interface ProtocolSession{
	public void handlePacket(WrappedPacket pk);
	public SocketAddress getAddress();
}
