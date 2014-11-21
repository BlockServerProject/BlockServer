package org.blockserver.network.protocol.pocket.subprotocol.v0_10_0;

import org.blockserver.Server;
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
}
