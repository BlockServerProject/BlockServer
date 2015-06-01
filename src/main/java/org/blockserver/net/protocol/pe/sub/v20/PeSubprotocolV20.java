package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.Server;
import org.blockserver.net.internal.request.DisconnectRequest;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.internal.request.PingRequest;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.net.protocol.pe.sub.PeDataPacketParser;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;
import org.blockserver.net.protocol.pe.sub.v27.DisconnectPacket;

public class PeSubprotocolV20 extends PeSubprotocol{
	private Server server;
	protected PeDataPacketParser parser;
	public PeSubprotocolV20(Server server){
		this.server = server;
		parser = new PeDataPacketParser(server);

		parser.add(MC_PLAY_PING, PingPacket.class);
		parser.add(MC_PLAY_PONG, PongPacket.class);
	}

	@Override
	public InternalRequest toInternalRequest(PeDataPacket dp){
		byte pid = dp.getPid();
		server.getLogger().debug("Adapting into request: " + dp.getPid());
		switch(pid){

			case MC_PLAY_PING:
				PingRequest pingRequest = new PingRequest();
				pingRequest.pingId = ((PingPacket) dp).pingID;
				return pingRequest;

			case MC_PLAY_PONG:
				//TODO
				return null;

			case MC_CANCEL_CONNECT:
				DisconnectRequest disconnectRequest = new DisconnectRequest();
				disconnectRequest.reason = "Client Disconnected.";
				return disconnectRequest;

			default:
				//TODO
				server.getLogger().debug("Unhandled data packet (PID: %x)", dp.getPid());
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
	public Server getServer(){
		return server;
	}
}
