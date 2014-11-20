package org.blockserver.network.protocol;

import org.blockserver.network.WrappedPacket;

public abstract class Protocol{
	public abstract ProtocolSession openSession(WrappedPacket pk);
}
