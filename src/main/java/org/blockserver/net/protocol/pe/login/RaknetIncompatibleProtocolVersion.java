package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class RaknetIncompatibleProtocolVersion implements PeProtocolConst{
	private ByteBuffer bb;
	public RaknetIncompatibleProtocolVersion(byte[] magic, long serverId){
		bb = ByteBuffer.allocate(26);
		bb.put(RAKNET_INCOMPATIBLE_PROTOCOL_VERSION);
		bb.put(magic);
		bb.putLong(serverId);
	}
	public byte[] getBuffer(){
		return bb.array();
	}
}
