package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class LoginStatusPacket extends BaseDataPacket{
	public int status;

	public LoginStatusPacket(int status){
		this.status = status;
		bb = ByteBuffer.allocate(5);
	}

	@Override
	public void encode(){
		bb.put(LOGIN_STATUS);
		bb.putInt(status);
	}

	@Override
	public void decode(){
		throw new UnsupportedOperationException();
	}
}
