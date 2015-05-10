package org.blockserver.api.event.net.protocol.pe;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.protocol.pe.RakNetProtocolSession;
import org.blockserver.net.protocol.pe.RakNetProtocolSession;

/**
 * Fired when a new encapsulated packet is received.
 * NOTE: If you cancel this event, the server WILL NOT run the handling code for this packet.
 */
public class PEDataPacketRecieveNativeEvent extends NativeEvent{

	private RakNetProtocolSession session;

	/*
	public PEDataPacketRecieveNativeEvent(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket packet, RakNetProtocolSession session){
		this.packet = packet;
		this.session = session;
	}
	public RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket getPacket(){
		return packet;
	}
	public void setPacket(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket packet){
		this.packet = packet;
	}

	public RakNetProtocolSession getSession(){
		return session;
	}
	*/
}
