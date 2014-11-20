package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

import org.blockserver.network.protocol.pocket.PocketProtocolConstants;

public class RaknetIncompatibleProtocolVersion implements PocketProtocolConstants{
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
