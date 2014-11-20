package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

import org.blockserver.network.protocol.pocket.PocketProtocolConstants;

public class RaknetOpenConnectionReply2 implements PocketProtocolConstants{
	private ByteBuffer bb;
	public RaknetOpenConnectionReply2(byte[] magic, short port, short mtu){
		bb = ByteBuffer.allocate(30);
		bb.put(magic);
		bb.putLong(SERVER_ID);
		bb.putShort(port);
		bb.putShort(mtu);
		bb.put((byte) 0);
	}
	public byte[] getBuffer(){
		return bb.array();
	}
}
