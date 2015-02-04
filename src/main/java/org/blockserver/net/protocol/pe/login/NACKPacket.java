package org.blockserver.net.protocol.pe.login;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class NACKPacket extends AcknowledgePacket implements PeProtocolConst {
	
	public NACKPacket(int[] packetNumbers){
		sequenceNumbers = packetNumbers;
	}
	
	public NACKPacket(byte[] buffer){
		buf = buffer;
	}
	
	public byte[] getBuffer(){
		return buf;
	}
	
	@Override
	public byte getPID(){
		return RAKNET_NACK;
	}

}
