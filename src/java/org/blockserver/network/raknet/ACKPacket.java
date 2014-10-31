package org.blockserver.network.raknet;

import org.blockserver.network.RaknetIDs;

public class ACKPacket extends AcknowledgePacket{
	public byte getPID() {
		return RaknetIDs.ACK;
	}

	public ACKPacket(int[] numbers){
		sequenceNumbers = numbers;
	}
	public ACKPacket(byte[] buffer){
		this.buffer = buffer;
	}

	@Override
	public byte[] getBuffer(){
		return buffer;
	}
}
