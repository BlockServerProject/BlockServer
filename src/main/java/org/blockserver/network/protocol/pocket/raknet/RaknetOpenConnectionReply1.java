package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

import org.blockserver.network.protocol.pocket.PocketProtocolConstants;

public class RaknetOpenConnectionReply1 implements PocketProtocolConstants{
	private ByteBuffer bb;
	public RaknetOpenConnectionReply1(byte[] magic, int mtu){
		bb = ByteBuffer.allocate(28);
		bb.put(RAKNET_OPEN_CONNECTION_REPLY_1);
		bb.put(magic);
		bb.putLong(SERVER_ID);
		bb.put((byte) 0);
		bb.putShort((short) mtu);
	}
	public byte[] getBuffer(){
		return bb.array();
	}
}
