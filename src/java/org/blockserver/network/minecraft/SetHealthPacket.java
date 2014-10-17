package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class SetHealthPacket extends BaseDataPacket{
	
	public byte health;
	
	public SetHealthPacket(byte health){
		this.health = health;
	}
	
	public void encode(){
		bb = ByteBuffer.allocate( 2);
		bb.put( SET_HEALTH );
		bb.put( (byte) health );
	}
	
	public void decode(){
		throw new UnsupportedOperationException("This packet is Server to Client side only and cannot be decoded.");
	}
}
