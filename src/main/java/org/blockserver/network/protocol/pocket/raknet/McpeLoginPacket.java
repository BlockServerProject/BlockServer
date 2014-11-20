package org.blockserver.network.protocol.pocket.raknet;

import java.nio.ByteBuffer;

import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocol;
import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocolManager;

public class McpeLoginPacket{
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
	public PocketSubprotocol getPreferredSubprotocol(PocketSubprotocolManager mgr){
		return mgr.findProtocol(protocol1, protocol2);
	}
}
