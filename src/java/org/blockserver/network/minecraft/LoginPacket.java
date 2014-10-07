package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class LoginPacket extends BaseDataPacket{
	public String username;
	public int protocol;
	public int protocol2;
	public int clientID;
	public String loginData;

	public LoginPacket(byte[] buffer){
		bb = ByteBuffer.wrap(buffer);
	}

	@Override
	public void encode(){

	}

	@Override
	public void decode(){
		if(bb.get() != LOGIN){
			throw new RuntimeException(String.format("Trying to decode packet LoginPacket and received %02X.", bb.array()[0]));
		}
		username = getString();
		protocol = bb.getInt();
		protocol2 = bb.getInt();
		clientID = bb.getInt();
		loginData = getString();
	}

	public String getString(){
		byte[] text = new byte[bb.getShort()];
		bb.get(text);
		return new String(text);
	}
}
