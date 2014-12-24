package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class RaknetOpenConnectionReply2 implements PeProtocolConst{
	private ByteBuffer bb;
	public RaknetOpenConnectionReply2(byte[] magic, short port, short mtu){
		bb = ByteBuffer.allocate(30);
		bb.put(RAKNET_OPEN_CONNECTION_REPLY_2);
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
