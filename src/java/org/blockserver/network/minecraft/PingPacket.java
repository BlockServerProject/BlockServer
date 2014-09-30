package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * Represents a 0x00 PING Packet.
 */
public class PingPacket extends BaseDataPacket {
	public long pingID;

	public PingPacket(byte[] b){
		bb = ByteBuffer.wrap(b);
	}
	public PingPacket(long pingID){
		this.pingID = pingID;
		bb = ByteBuffer.allocate(9);
	}

	@Override
	public void decode(){
		pingID = bb.getLong();
	}

	@Override
	public void encode(){
		bb.put(PING);
		bb.putLong(pingID);
	}
}
