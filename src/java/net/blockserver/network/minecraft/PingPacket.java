package net.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * Represents a 0x00 PING Packet.
 */

public class PingPacket implements BaseDataPacket {

    public ByteBuffer buffer;
	public final byte PID = PacketsID.PING;
	public long pingID;

	public PingPacket(byte[] b)
    {
        this.buffer = ByteBuffer.wrap(b);
	}

    public PingPacket(long pingID)
    {
        this.buffer = ByteBuffer.allocate(9);
        this.pingID = pingID;
    }

	public void decode(){
		if(this.buffer.get() != this.PID)
            return;

		pingID = this.buffer.getLong();
	}
	
	public void encode()
    {
        this.buffer.put(this.PID);
        this.buffer.putLong(this.pingID);
    }
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
