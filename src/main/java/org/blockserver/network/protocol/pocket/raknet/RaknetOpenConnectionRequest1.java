package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

public class RaknetOpenConnectionRequest1{
	public byte[] magic = new byte[16];
	public byte raknetVersion;
	public int payloadLength;
	public RaknetOpenConnectionRequest1(ByteBuffer bb){
		bb.get(magic);
		raknetVersion = bb.get();
		payloadLength = bb.remaining();
	}
}
