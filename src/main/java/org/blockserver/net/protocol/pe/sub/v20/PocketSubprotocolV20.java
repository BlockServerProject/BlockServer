package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.sub.generic.PocketSubprotocolGeneric;
import org.blockserver.net.protocol.pe.sub.generic.StartGamePacket;

public class PocketSubprotocolV20 extends PocketSubprotocolGeneric{
	public PocketSubprotocolV20(Server server){
		super(server);
		parser.add(MC_START_GAME_PACKET, StartGamePacket.class);
		// TODO more
	}

	@Override
	public int getSubprotocolVersionId(){
		return 20;
	}

	@Override
	public String getSubprotocolName(){
		return "V20 compatible for MCPE alpha 0.10.4";
	}
	
}
