package org.blockserver.net.protocol;


public abstract class Protocol{
	public abstract ProtocolSession openSession(WrappedPacket pk);
	public abstract String getDescription();
}
