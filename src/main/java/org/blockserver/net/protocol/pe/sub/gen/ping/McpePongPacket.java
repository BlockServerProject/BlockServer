package org.blockserver.net.protocol.pe.sub.gen.ping;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

public class McpePongPacket extends PeDataPacket{
	public long pingID;

	public McpePongPacket(long pingId){
		super(new byte[]{MC_PLAY_PONG});
		pingID = pingId;
	}

	@Override
	protected void _encode(BinaryWriter writer) throws IOException{
		writer.writeByte(MC_PLAY_PONG);
		writer.writeLong(pingID);
	}

	@Override
	public int getLength(){
		return 9;
	}
}
