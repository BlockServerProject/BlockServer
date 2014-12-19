package org.blockserver.net.protocol.pe.raknet;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.PeSubprotocolMgr;

public class McpeLoginPacket implements PeProtocolConst{
	public String username;
	public int protocol1;
	public int protocol2;
	public int clientId;
	public String loginData;
	public McpeLoginPacket(ByteBuffer bb){
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
	public PeSubprotocol getPreferredSubprotocol(PeSubprotocolMgr mgr){
		return mgr.findProtocol(protocol1, protocol2);
	}
}
