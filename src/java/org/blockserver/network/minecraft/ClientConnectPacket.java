package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class ClientConnectPacket implements BaseDataPacket{
	private ByteBuffer buffer;
	public long clientID;
	public long session;
	public byte unknown;
	
	public ClientConnectPacket(byte[] data){
		buffer = ByteBuffer.wrap(data);
	}

	@Override
	public void decode(){
		if(buffer.get() != PacketsID.CLIENT_CONNECT){
			return;
		}
		clientID = buffer.getLong();
		session = buffer.getLong();
		unknown = buffer.get();
	}

	@Override
	public void encode(){}

	@Override
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
