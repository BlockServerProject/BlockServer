package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * Implements a 0x03 PONG Packet.
 */
public class PongPacket extends BaseDataPacket{
	public long pingID;
	public long unknown;
	
	public PongPacket(long pingID){
		this.pingID = pingID;
		unknown = pingID + System.currentTimeMillis();
		bb = ByteBuffer.allocate(17);
	}
	public PongPacket(byte[] buffer){
		bb = ByteBuffer.wrap(buffer);
	}

	@Override
	public void encode(){
		bb.put(PONG);
		bb.putLong(pingID);
		bb.putLong(unknown);
	}

	@Override
	public void decode(){ // We can send the Ping Packet and receive a Pong Packet. To check if the player is gone or have lag
		pingID = bb.getLong();
		unknown = bb.getLong();
	}
}
