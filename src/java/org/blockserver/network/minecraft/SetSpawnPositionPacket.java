package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.math.Vector3;

public class SetSpawnPositionPacket extends BaseDataPacket{
	protected Vector3 vector;
	
	public SetSpawnPositionPacket(Vector3 vector) {
		this.vector = vector;
	}
	
	public void encode(){
		bb = ByteBuffer.allocate( 1 + (Integer.BYTES * 2) + 1 );
		bb.put( SET_SPAWN_POSITION );
		bb.putInt( vector.getX() );
		bb.putInt( vector.getZ() );
		bb.put( (byte) vector.getY() );
	}
	
	public void decode(){
		throw new UnsupportedOperationException("This packet is Server to Client side only and cannot be decoded.");
	}
}
