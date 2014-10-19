package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.math.Vector3;
import org.blockserver.math.Vector3d;

public class StartGamePacket extends BaseDataPacket{
	public Vector3 spawnPos;
	public Vector3d playerpos;
	
	public long seed;
	public int generator;
	public int gamemode;
	public int eid;
	
	public StartGamePacket(Vector3 spawnPos, Vector3d playerpos, int gamemode, long seed, int eid){
		this.spawnPos = spawnPos;
		this.playerpos = playerpos;
		
		this.gamemode = gamemode;
		this.seed = seed;
		this.eid = eid;
	}

	@Override
	public void encode(){
		bb = ByteBuffer.allocate(1 + (0x04 * 11)); //Not sure about this, I think its right
		bb.put(START_GAME); 
		bb.putInt((int) seed);
		bb.putInt(generator);
		bb.putInt(gamemode);
		bb.putInt(eid);
		bb.putInt(spawnPos.getX());
		bb.putInt(spawnPos.getY());
		bb.putInt(spawnPos.getZ());
		bb.putFloat((float) playerpos.getX());
		bb.putFloat((float) playerpos.getY());
		bb.putFloat((float) playerpos.getZ());
	}

	@Override
	public void decode(){}
}
