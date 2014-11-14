package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class SetTimePacket extends BaseDataPacket{
	public int time;
	
	public SetTimePacket(int time) {
		this.time = time;
	}
	
	public void encode(){
		bb = ByteBuffer.allocate( 2 + 0x04 );
		bb.put(SET_TIME);
		bb.putInt(time);
		//TODO 0x00
		bb.put((byte) 0x80);
	}
	
	public void decode(){
		throw new UnsupportedOperationException("This packet is Server to Client side only and cannot be decoded.");
	}
}
