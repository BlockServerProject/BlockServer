package org.blockserver.api.event.net.protocol.pe;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.protocol.pe.RakNetProtocolSession;
import org.blockserver.net.protocol.pe.RakNetProtocolSession;
import org.blockserver.net.protocol.pe.login.RaknetSentCustomPacket;

/**
 * Fired whenever a RakNet Custom packet is to be sent.
 */
public class PEDataPacketSendNativeEvent extends NativeEvent{
	private RaknetSentCustomPacket.SentEncapsulatedPacket packet;
	private RakNetProtocolSession session;

	public PEDataPacketSendNativeEvent(RaknetSentCustomPacket.SentEncapsulatedPacket packet, RakNetProtocolSession session){
		this.packet = packet;
		this.session = session;
	}

	public RaknetSentCustomPacket.SentEncapsulatedPacket getPacket(){
		return packet;
	}
	public void setPacket(RaknetSentCustomPacket.SentEncapsulatedPacket packet){
		this.packet = packet;
	}

	public RakNetProtocolSession getSession(){
		return session;
	}
}
