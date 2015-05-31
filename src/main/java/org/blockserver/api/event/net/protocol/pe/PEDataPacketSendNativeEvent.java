package org.blockserver.api.event.net.protocol.pe;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.protocol.pe.RakNetProtocolSession;
import org.blockserver.net.protocol.pe.raknet.CustomPacket;

/**
 * Fired whenever a RakNet Custom packet is to be sent.
 */
public class PEDataPacketSendNativeEvent extends NativeEvent{

	private CustomPacket packet;
	private RakNetProtocolSession session;

	public PEDataPacketSendNativeEvent(CustomPacket packet, RakNetProtocolSession session){
		this.packet = packet;
		this.session = session;
	}

	public CustomPacket getPacket(){
		return packet;
	}
	public void setPacket(CustomPacket packet){
		this.packet = packet;
	}

	public RakNetProtocolSession getSession(){
		return session;
	}

}
