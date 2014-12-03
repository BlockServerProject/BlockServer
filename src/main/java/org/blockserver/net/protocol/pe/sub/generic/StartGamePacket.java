package org.blockserver.net.protocol.pe.sub.generic;

import java.io.IOException;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PocketDataPacket;

public class StartGamePacket extends PocketDataPacket{
	public int seed;
	public int generator;
	public int gamemode;
	public int eid;
	public int spawnX;
	public int spawnY;
	public int spawnZ;
	public float x;	
	public float y;
	public float z;
	@Override
	public byte getPid(){
		return MC_START_GAME_PACKET;
	}
	@Override
	protected int getLength(){
		return 0;
	}
	@Override
	protected void _encode(BinaryWriter writer) throws IOException{
		writer.writeInt(seed);
		writer.writeInt(generator);
		writer.writeInt(gamemode);
		writer.writeInt(eid);
		writer.writeInt(spawnX);
		writer.writeInt(spawnY);
		writer.writeInt(spawnZ);
		writer.writeFloat(x);
		writer.writeFloat(y);
		writer.writeFloat(z);
	}
}
