package org.blockserver.network.protocol.pocket.subprotocol;

import org.blockserver.Server;

public abstract class PocketSubprotocol{
	public abstract Server getServer();
	public abstract int getSubprotocolVersionId();
	public abstract String getSubprotocolName();
}
