package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.Server;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.sub.gen.McpeDisconnectPacket;
import org.blockserver.net.protocol.pe.sub.gen.PeSubprotocolGen;
import org.blockserver.net.protocol.pe.sub.gen.McpeStartGamePacket;

public class PeSubprotocolV20 extends PeSubprotocolGen{
	public PeSubprotocolV20(Server server){
		super(server);
		parser.add(MC_START_GAME_PACKET, McpeStartGamePacket.class);
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
