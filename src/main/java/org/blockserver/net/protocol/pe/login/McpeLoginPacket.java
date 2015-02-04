package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.PeSubprotocolMgr;

/**
 * An implementation of a LoginPacket (0x82).
 * @author PEMapModder, jython234
 *
 */
public class McpeLoginPacket implements PeProtocolConst, EncapsulatedLoginPacket{
	/**
	 * The client's username when logging in.
	 */
	public String username;
	/**
	 * The client's protocol number (Unknown why two).
	 */
	public int protocol1;
	/**
	 * The client's other protocol number. Usually the same as the first one.
	 */
	public int protocol2;
	/**
	 * The Mojang Client's client ID
	 */
	public int clientId;
	/**
	 * Unknown string containing login data.
	 */
	public String loginData;
	private ByteBuffer bb;
	/**
	 * Constructor for LoginPacket(0x82).
	 * @param bb The ByteBuffer containing raw packet data.
	 */
	public McpeLoginPacket(ByteBuffer bb){
		this.bb = bb;
	}
	
	@Override
	public void encode() {
		throw new UnsupportedOperationException("This packet is only Client to Server.");
	}
	
	@Override
	public void decode(){
		byte[] userBuffer = new byte[bb.getShort()];
		bb.get(userBuffer);
		username = new String(userBuffer);
		protocol1 = bb.getInt();
		protocol2 = bb.getInt();
		clientId = bb.getInt();
		byte[] loginBuffer = new byte[bb.getShort()];
		bb.get(loginBuffer);
		loginData = new String(loginBuffer);
	}
	
	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
	
	/**
	 * Get the preferred SubProtocol depending on the protocol the client is using.
	 * @param mgr The SubProtocol manager currently being used.
	 * @return The SubProtocol for the version this client is using.
	 */
	public PeSubprotocol getPreferredSubprotocol(PeSubprotocolMgr mgr){
		return mgr.findProtocol(protocol1, protocol2);
	}
}
