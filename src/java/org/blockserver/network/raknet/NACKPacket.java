package org.blockserver.network.raknet;

import java.nio.ByteBuffer;

public class NACKPacket extends AcknowledgePacket {
	@Override
	public byte getPID() {
		return 0;
	}

	public NACKPacket(int[] numbers)
	{
		this.sequenceNumbers = numbers;
	}

	public NACKPacket(byte[] buffer)
	{
		this.buffer = buffer;
	}
	
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(buffer);
	}
}
