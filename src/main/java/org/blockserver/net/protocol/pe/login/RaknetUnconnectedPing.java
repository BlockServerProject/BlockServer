package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

public class RaknetUnconnectedPing{
	public long pingId;
	public byte[] magic = new byte[16];
	public RaknetUnconnectedPing(ByteBuffer bb){
		pingId = bb.getLong();
		bb.get(magic);
	}
}
