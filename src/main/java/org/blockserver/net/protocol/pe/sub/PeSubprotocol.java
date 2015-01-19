package org.blockserver.net.protocol.pe.sub;

import org.blockserver.Server;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket;
import org.blockserver.player.Player;

public abstract class PeSubprotocol implements PeProtocolConst{
	public abstract Server getServer();
	public abstract int getSubprotocolVersionId();
	public abstract String getSubprotocolName();
	public void readDataPacket(ReceivedEncapsulatedPacket pk, Player handler){
		PeDataPacket dp = getDataPacketByBuffer(pk.buffer);
		dp.decode(pk.buffer);
		//handleDataPacket(dp);
		InternalRequest req = toInternalRequest(dp);
		handler.handleRequest(req);
	}
	@Deprecated
	protected void handleDataPacket(PeDataPacket dp){}
	protected abstract InternalRequest toInternalRequest(PeDataPacket dp);
	public abstract PeDataPacket getDataPacketByBuffer(byte[] buffer);
}
