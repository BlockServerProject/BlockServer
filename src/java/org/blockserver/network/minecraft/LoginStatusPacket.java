package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class LoginStatusPacket implements BaseDataPacket{
	public int status;
	private ByteBuffer bb;

	public LoginStatusPacket(int status){
		this.status = status;
		bb = ByteBuffer.allocate(5);
	}

	@Override
	public void encode(){
		bb.put(PacketsID.LOGIN_STATUS);
		bb.putInt(status);
	}

	@Override
	public void decode(){
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
}
