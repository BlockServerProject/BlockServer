package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

import org.blockserver.network.protocol.pocket.PocketProtocolConstants;

public class RaknetUnconnectedPong implements PocketProtocolConstants{
	private ByteBuffer bb;
	public RaknetUnconnectedPong(long ping, long server, byte[] magic, String name){
		name = "MCCPP;DEMO;" + name;
		bb = ByteBuffer.allocate(30);
		bb.putLong(ping);
		bb.putLong(server);
		bb.put(magic);
		bb.putShort((short) name.length());
		bb.put(name.getBytes());
	}
	public byte[] getBuffer(){
		return bb.array();
	}
}
