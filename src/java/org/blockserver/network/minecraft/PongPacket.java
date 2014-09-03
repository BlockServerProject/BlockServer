package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * Implements a 0x03 PONG Packet.
 */
public class PongPacket implements BaseDataPacket{
	private ByteBuffer buffer;
	public final byte PID = PacketsID.PONG;
	public long pingID;
	public long unknown;
	
	public PongPacket(long pingID){
		this.pingID = pingID;
		this.unknown = pingID + System.currentTimeMillis();
	}
	public PongPacket(byte[] buffer){
		this.buffer = ByteBuffer.wrap(buffer);
	}

	@Override
	public void encode(){
		buffer = ByteBuffer.allocate(17);
		buffer.put(this.PID);
		buffer.putLong(this.pingID);
		buffer.putLong(this.unknown);
	}

	@Override
	public void decode(){ // We can send the Ping Packet and receive a Pong Packet. To check if the player is gone or have lagg
		if(buffer.get() != PID){
			return;
		}
		pingID = buffer.getLong();
		unknown = buffer.getLong();
	}

	@Override
	public ByteBuffer getBuffer(){
		return this.buffer;
	}
}
