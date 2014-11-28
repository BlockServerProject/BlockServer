package org.blockserver.network.protocol.pocket.subprotocol.v20;

import org.blockserver.Server;
import org.blockserver.network.protocol.pocket.subprotocol.PocketDataPacket;
import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocol;

public class PocketSubprotocolV20 extends PocketSubprotocol{
	private Server server;
	public PocketSubprotocolV20(Server server){
		this.server = server;
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	public int getSubprotocolVersionId(){
		return 20;
	}
	@Override
	public String getSubprotocolName(){
		return "0.10.0";
	}
	@Override
	protected void handleDataPacket(PocketDataPacket dp){
		// TODO Auto-generated method stub
		
	}
	@Override
	public PocketDataPacket getDataPacketByPid(byte pid){
		// TODO Auto-generated method stub
		return null;
	}
}
