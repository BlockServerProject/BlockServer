package org.blockserver.network.protocol;

public abstract class Protocol{
	public abstract ProtocolSession openSession(WrappedPacket pk);
}
