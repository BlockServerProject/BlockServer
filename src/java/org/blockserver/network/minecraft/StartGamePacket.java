package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public class StartGamePacket extends BaseDataPacket{
	public long seed;
	public int unknown;
	public int gamemode;
	public int eid;
	public float spawnX;
	public float spawnY;
	public float spawnZ;

	public StartGamePacket(Level level, int eid){
		this(level.getSpawnPos(), level.getDefaultGamemode(), level.getSeed(), eid);
	}
	public StartGamePacket(Vector3d spawnpos, int gamemode, long seed, int eid){
		spawnX = (float) spawnpos.getX();
		spawnY = (float) spawnpos.getY();
		spawnZ = (float) spawnpos.getZ();
		this.gamemode = gamemode;
		this.seed = seed;
		this.eid = eid;
	}

	@Override
	public void encode(){
		bb = ByteBuffer.allocate(29); //Not sure about this, I think its right
		bb.put(START_GAME);
		bb.putInt((int) seed);
		bb.putInt(unknown);
		bb.putInt(gamemode);
		bb.putInt(eid);
		bb.putFloat(spawnX);
		bb.putFloat(spawnY);
		bb.putFloat(spawnZ);
	}

	@Override
	public void decode(){}
}
