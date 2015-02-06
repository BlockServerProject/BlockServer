package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.Server;
import org.blockserver.net.internal.request.DisconnectRequest;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.internal.request.PingRequest;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.net.protocol.pe.sub.PeDataPacketParser;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.gen.McpeDisconnectPacket;
import org.blockserver.net.protocol.pe.sub.gen.McpeStartGamePacket;
import org.blockserver.net.protocol.pe.sub.gen.ping.McpePingPacket;

public class PeSubprotocolV20 extends PeSubprotocol {
	private Server server;
	protected PeDataPacketParser parser;
	public PeSubprotocolV20(Server server){
		this.server = server;
		parser = new PeDataPacketParser(server);
		parser.add(MC_START_GAME_PACKET, McpeStartGamePacket.class);
		parser.add(MC_PLAY_PING, McpePingPacket.class);
		parser.add(MC_DISCONNECT, McpeDisconnectPacket.class);
		// TODO more
	}

	@Override
	public InternalRequest toInternalRequest(PeDataPacket dp){
		byte pid = dp.getPid();
		server.getLogger().debug("Adapting into request: "+dp.getPid());
		switch(pid){
			case MC_PLAY_PING:
				McpePingPacket pingPacket = (McpePingPacket) dp;
				PingRequest pingRequest = new PingRequest();
				pingRequest.pingId = pingPacket.pingID;

				return pingRequest;

			case MC_DISCONNECT:
				McpeDisconnectPacket disconnectPacket = (McpeDisconnectPacket) dp;
				DisconnectRequest disconnectRequest = new DisconnectRequest();
				disconnectRequest.reason = "Disconnected by client.";

				return disconnectRequest;
			default:
				//TODO
				return null;
		}

	}

	@Override
	public int getSubprotocolVersionId(){
		return 20;
	}

	@Override
	public PeDataPacket getDataPacketByBuffer(byte[] buffer){
		return parser.parsePacket(buffer);
	}

	@Override
	public String getSubprotocolName(){
		return "V20 compatible for MCPE alpha 0.10.4";
	}

	@Override
	public Server getServer(){ return server; }
	
}
