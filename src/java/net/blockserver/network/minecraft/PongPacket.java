package net.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * Implements a 0x03 PONG Packet.
 */
public class PongPacket implements BaseDataPacket {

	private ByteBuffer buffer;
	
	public final byte PID = PacketsID.PONG;
	public long pingID;
	public long unknown;
	
	public PongPacket(long pingID){
		this.pingID = pingID;
		this.unknown = pingID + System.currentTimeMillis();
	}

	public PongPacket(byte[] buffer)
	{
		this.buffer = ByteBuffer.wrap(buffer);
	}

	public void encode(){
		buffer = ByteBuffer.allocate(17);
		
		buffer.put(this.PID);
		buffer.putLong(this.pingID);
		buffer.putLong(this.unknown);
	}
	
	public void decode() // We can send the Ping Packet and receive a Pong Packet. To check if the player is gone or have lagg
	{
		if (this.buffer.get() != this.PID)
			return;

		this.pingID = this.buffer.getLong();
		this.unknown = this.buffer.getLong();

	}

	public ByteBuffer getBuffer(){
		return this.buffer;
	}

}
