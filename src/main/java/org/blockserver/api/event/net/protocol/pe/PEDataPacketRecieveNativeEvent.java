package org.blockserver.api.event.net.protocol.pe;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket;

/**
 * Fired when a new encapsulated packet is received.
 * NOTE: If you cancel this event, the server WILL NOT run the handling code for this packet.
 */
public class PEDataPacketRecieveNativeEvent extends NativeEvent{
	private RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket packet;
	private PeProtocolSession session;

	public PEDataPacketRecieveNativeEvent(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket packet, PeProtocolSession session){
		this.packet = packet;
		this.session = session;
	}
	public RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket getPacket(){
		return packet;
	}
	public void setPacket(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket packet){
		this.packet = packet;
	}

	public PeProtocolSession getSession(){
		return session;
	}
}
