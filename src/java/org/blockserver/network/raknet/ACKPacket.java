package org.blockserver.network.raknet;

import java.nio.ByteBuffer;

import org.blockserver.network.RaknetsID;

public class ACKPacket extends AcknowledgePacket {
	public byte getPID() {
		return RaknetsID.ACK;
	}

	public ACKPacket(int[] numbers)
	{
		this.sequenceNumbers = numbers;
	}

	public ACKPacket(byte[] buffer)
	{
		this.buffer = buffer;
	}
	
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(buffer);
	}
}
