package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

public class RaknetUnconnectedPing{
	public long pingId;
	public byte[] magic = new byte[16];
	public RaknetUnconnectedPing(ByteBuffer bb){
		pingId = bb.getLong();
		bb.get(magic);
	}
}
