package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * Represents a 0x00 PING Packet.
 */
public class PingPacket implements BaseDataPacket {
	public ByteBuffer buffer;
	public final byte PID = PacketsID.PING;
	public long pingID;

	public PingPacket(byte[] b){
		buffer = ByteBuffer.wrap(b);
	}
	public PingPacket(long pingID){
		buffer = ByteBuffer.allocate(9);
		this.pingID = pingID;
	}

	@Override
	public void decode(){
		if(buffer.get() != PID)
			return;

		pingID = buffer.getLong();
	}

	@Override
	public void encode(){
		buffer.put(PID);
		buffer.putLong(pingID);
	}

	@Override
	public ByteBuffer getBuffer(){
		return buffer;
	}
}
