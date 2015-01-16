package org.blockserver.net.protocol.pe.sub;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket;

public abstract class PeSubprotocol implements PeProtocolConst{
	public abstract Server getServer();
	public abstract int getSubprotocolVersionId();
	public abstract String getSubprotocolName();
	public void readDataPacket(ReceivedEncapsulatedPacket pk){
		PeDataPacket dp = getDataPacketByBuffer(pk.buffer);
		handleDataPacket(dp);
	}
	protected abstract void handleDataPacket(PeDataPacket dp);
	public abstract PeDataPacket getDataPacketByBuffer(byte[] buffer);
}
