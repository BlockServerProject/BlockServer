package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class McpeClientConnectPacket implements PeProtocolConst{
	public long clientId;
	public long session;
	public byte unknown;
	public McpeClientConnectPacket(ByteBuffer bb){
		clientId = bb.getLong();
		session = bb.getLong();
		unknown = bb.get();
	}
}
