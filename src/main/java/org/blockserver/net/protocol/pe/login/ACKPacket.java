package org.blockserver.net.protocol.pe.login;

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
	
	@Override
	public byte getPID(){
		return RAKNET_ACK;
	}

}
