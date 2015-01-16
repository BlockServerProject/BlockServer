package org.blockserver.net.protocol.pe.sub.gen;

import java.io.IOException;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

public class McpeStartGamePacket extends PeDataPacket{
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
	public McpeStartGamePacket(){
		super(new byte[] { MC_START_GAME_PACKET});
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
