package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.net.protocol.pe.sub.PeDataPacketParser;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;

public abstract class PeSubprotocolGen extends PeSubprotocol{
	private Server server;
	protected PeDataPacketParser parser;
	public PeSubprotocolGen(Server server){
		this.server = server;
		parser = new PeDataPacketParser(server);
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	protected void handleDataPacket(PeDataPacket dp){
		// TODO Auto-generated method stub
		
	}
	@Override
	public PeDataPacket getDataPacketByBuffer(byte[] buffer){
		return parser.parsePacket(buffer);
	}
}
