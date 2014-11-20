package org.blockserver.network.bridge;

import java.net.SocketAddress;

import org.blockserver.network.WrappedPacket;

public abstract class NetworkBridge{
	public abstract boolean send(byte[] buffer, SocketAddress addr);
	public abstract WrappedPacket receive();
}
