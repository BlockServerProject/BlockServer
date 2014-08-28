package org.blockserver.network.raknet;

import java.nio.ByteBuffer;

public class NACKPacket extends AcknowledgePacket {
	public NACKPacket(int[] numbers){
		sequenceNumbers = numbers;
	}
	public NACKPacket(byte[] buffer){
		this.buffer = buffer;
	}

	@Override
	public byte getPID(){
		return 0;
	}
	@Override
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(buffer);
	}
}
