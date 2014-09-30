package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class ClientConnectPacket extends BaseDataPacket{
	public long clientID;
	public long session;
	public byte unknown;
	
	public ClientConnectPacket(byte[] data){
		bb = ByteBuffer.wrap(data);
	}

	@Override
	public void decode(){
		if(bb.get() != CLIENT_CONNECT){
			return;
		}
		clientID = bb.getLong();
		session = bb.getLong();
		unknown = bb.get();
	}

	@Override
	public void encode(){}
}
