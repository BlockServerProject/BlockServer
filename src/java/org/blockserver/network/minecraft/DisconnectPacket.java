package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class DisconnectPacket extends BaseDataPacket{
	public DisconnectPacket(){
		bb = ByteBuffer.allocate(1);
	}
	public DisconnectPacket(byte[] buffer){
		bb = ByteBuffer.wrap(buffer);
	}

	@Override
	public void encode(){
		bb.put(DISCONNECT);
	}

	@Override
	public void decode(){
		throw new RuntimeException(String.format("Trying to decode Disconnect packet and received: %02X", bb.array()[0]));
	}
}
