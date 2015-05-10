package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.Server;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.net.protocol.pe.sub.PeDataPacketParser;
import org.blockserver.net.protocol.pe.sub.PeSubprotocol;

public class PeSubprotocolV20 extends PeSubprotocol{
	private Server server;
	protected PeDataPacketParser parser;
	public PeSubprotocolV20(Server server){
		this.server = server;
		// TODO more
	}

	@Override
	public InternalRequest toInternalRequest(PeDataPacket dp){
		byte pid = dp.getPid();
		server.getLogger().debug("Adapting into request: " + dp.getPid());
		switch(pid){
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
