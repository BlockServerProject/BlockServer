package org.blockserver.network.protocol.pocket.subprotocol;

import org.blockserver.Server;
import org.blockserver.network.protocol.pocket.PocketProtocolConstants;
import org.blockserver.network.protocol.pocket.raknet.RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket;

public abstract class PocketSubprotocol implements PocketProtocolConstants{
	public abstract Server getServer();
	public abstract int getSubprotocolVersionId();
	public abstract String getSubprotocolName();
	public void readDataPacket(ReceivedEncapsulatedPacket pk){
		PocketDataPacket dp = getDataPacketByBuffer(pk.buffer);
		handleDataPacket(dp);
	}
	protected abstract void handleDataPacket(PocketDataPacket dp);
	public abstract PocketDataPacket getDataPacketByBuffer(byte[] buffer);
}
