package org.blockserver.net.protocol.pe.raknet;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class ACKPacket extends AcknowledgePacket implements PeProtocolConst {
	
	public ACKPacket(int[] packetNumbers){
		sequenceNumbers = packetNumbers;
	}
	
	public ACKPacket(byte[] buffer){
		buf = buffer;
	}
	
	public byte[] getBuffer(){
		return buf;
	}
	
	public byte getPID(){
		return RAKNET_ACK;
	}

}
