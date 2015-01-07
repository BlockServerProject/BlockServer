package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class RaknetUnconnectedPong implements PeProtocolConst{
	private ByteBuffer bb;
	public RaknetUnconnectedPong(long ping, long server, byte[] magic, String name){
		name = "MCCPP;Demo;" + name;
		byte[] nameBytes = name.getBytes();
		bb = ByteBuffer.allocate(35 + nameBytes.length);
		bb.put(RAKNET_BROADCAST_PONG_1);
		bb.putLong(ping);
		bb.putLong(server);
		bb.put(MAGIC);
		bb.putShort((short) nameBytes.length);
		bb.put(nameBytes);
	}
	public byte[] getBuffer(){
		return bb.array();
	}
}
