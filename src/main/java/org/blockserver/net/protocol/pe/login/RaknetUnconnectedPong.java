package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;

public class RaknetUnconnectedPong implements PeProtocolConst{
	private ByteBuffer bb;
	public RaknetUnconnectedPong(long ping, long server, byte[] magic, String name){
		int protocol_version = 27; //Minecraft PE Protocol Version
		String mc_version = "0.11.1"; //Minecraft PE Version String
		int online_players = 0; //Number of players online in the server
		int max_players = 20; //Number of players that can join in total
		name = "MCPE;" + name + ";" + protocol_version + ";" + mc_version + ";" + online_players + ";" + max_players;
		byte[] nameBytes = name.getBytes();
		bb = ByteBuffer.allocate(35 + nameBytes.length);
		bb.put(RAKNET_BROADCAST_PONG_1);
		bb.putLong(ping);
		bb.putLong(server);
		bb.put(MAGIC);
		bb.putShort((short) nameBytes.length);
		bb.put(nameBytes);
	}
	public byte[] getBuffer(){
		return bb.array();
	}
}
