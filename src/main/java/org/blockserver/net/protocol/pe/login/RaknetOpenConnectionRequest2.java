package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

public class RaknetOpenConnectionRequest2{
	public byte[] magic = new byte[16];
	public byte security;
	public int cookie;
	public short serverPort;
	public short mtu;
	public long clientId;
	public RaknetOpenConnectionRequest2(ByteBuffer bb){
		bb.get(magic);
		security = bb.get();
		cookie = bb.getInt();
		serverPort = bb.getShort();
		mtu = bb.getShort();
		clientId = bb.getLong();
	}
}
