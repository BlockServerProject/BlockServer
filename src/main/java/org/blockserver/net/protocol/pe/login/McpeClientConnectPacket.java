package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;

/**
 * An implementation of a ClientConnectPacket(0x09).
 * @author jython234
 *
 */
public class McpeClientConnectPacket implements PeProtocolConst, EncapsulatedLoginPacket{
	/**
	 * The Mojang Client's Client ID.
	 */
	public long clientId;
	/**
	 * A 64 bit long to be returned in ServerHandshake(0x10).
	 */
	public long session;
	
	private ByteBuffer bb;
	/**
	 * Constructor for ClientConnectPacket.
	 * @param bb The bytebuffer containing raw packet data.
	 */
	public McpeClientConnectPacket(ByteBuffer bb){
		this.bb = bb;
	}
	
	@Override
	public void encode() { }
	
	@Override
	public void decode(){
		clientId = bb.getLong();
		session = bb.getLong();
		bb.get();
	}
	
	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
}
