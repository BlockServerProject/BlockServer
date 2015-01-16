package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.PeProtocol;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.net.protocol.pe.sub.PeDataPacketParser;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.gen.ping.McpePingPacket;
import org.blockserver.net.protocol.pe.sub.gen.ping.McpePongPacket;

public abstract class PeSubprotocolGen extends PeSubprotocol{
	private Server server;
	protected PeDataPacketParser parser;
	public PeSubprotocolGen(Server server){
		this.server = server;
		parser = new PeDataPacketParser(server);
		parser.add(MC_DISCONNECT, McpeDisconnectPacket.class);
		parser.add(MC_PLAY_PING, McpePingPacket.class);
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	protected void handleDataPacket(PeDataPacket dp){
		// TODO Auto-generated method stub
		getServer().getLogger().debug("Recieved Packet at org.blockserver.net.protocol.pe.sub.gen.PeSubprotocolGen.handleDataPacket()");
		if(dp.getPid() == MC_DISCONNECT) {
			getServer().getLogger().debug("Recieved Disconnect!");
		} else if(dp.getPid() == MC_PLAY_PING){
			McpePingPacket ping = (McpePingPacket) dp;
			McpePongPacket pong = new McpePongPacket(ping.pingID);

		}
	}
	@Override
	public PeDataPacket getDataPacketByBuffer(byte[] buffer){
		return parser.parsePacket(buffer);
	}
}
