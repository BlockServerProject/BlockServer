package org.blockserver.network.protocol.pocket.subprotocol.v0_10_0;

import org.blockserver.Server;
import org.blockserver.network.protocol.pocket.raknet.RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket;
import org.blockserver.network.protocol.pocket.subprotocol.PocketDataPacket;
import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocol;

public class PocketSubprotocol_0_10_0 extends PocketSubprotocol{
	private Server server;
	public PocketSubprotocol_0_10_0(Server server){
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
