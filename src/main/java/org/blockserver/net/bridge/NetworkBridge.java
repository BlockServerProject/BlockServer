package org.blockserver.net.bridge;

import java.net.SocketAddress;

import org.blockserver.Server;
import org.blockserver.net.protocol.WrappedPacket;

public abstract class NetworkBridge{
	public abstract boolean send(byte[] buffer, SocketAddress addr);
	public abstract WrappedPacket receive();
	public abstract Server getServer();
	public abstract String getDescription();
}
