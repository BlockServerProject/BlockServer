package org.blockserver.net.protocol.pe.sub.generic;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.sub.PocketDataPacket;
import org.blockserver.net.protocol.pe.sub.PocketDataPacketParser;
import org.blockserver.net.protocol.pe.sub.PocketSubprotocol;

public abstract class PocketSubprotocolGeneric extends PocketSubprotocol{
	private Server server;
	protected PocketDataPacketParser parser;
	public PocketSubprotocolGeneric(Server server){
		this.server = server;
		parser = new PocketDataPacketParser(server);
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	protected void handleDataPacket(PocketDataPacket dp){
		// TODO Auto-generated method stub
		
	}
	@Override
	public PocketDataPacket getDataPacketByBuffer(byte[] buffer){
		return parser.parsePacket(buffer);
	}
}
