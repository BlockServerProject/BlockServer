package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3;

public class StartGamePacket implements BaseDataPacket{
	protected ByteBuffer buffer;
	private Level level;
	public byte PID = PacketsID.START_GAME;
	public int seed;
	public int unknown;
	public int gamemode;
	public int eid;
	public float spawnX;
	public float spawnY;
	public float spawnZ;

	public StartGamePacket(Level level, int eid){
		this.level = level;
		this.eid = eid;
		seed = level.getSeed();
		unknown = 1;
		gamemode = level.getDefaultGamemode();
		spawnX = (float) level.getSpawnPos().getX();
		spawnY = (float) level.getSpawnPos().getY();
		spawnZ = (float) level.getSpawnPos().getZ();
	}
	
	public StartGamePacket(Vector3 spawnpos, int gamemode, int seed, int eid){
		this.spawnX = spawnpos.getX();
		this.spawnY = spawnpos.getY();
		this.spawnZ = spawnpos.getZ();
		
		this.gamemode = gamemode;
		this.seed = seed;
		this.eid = eid;
	}

	@Override
	public void encode(){
		buffer = ByteBuffer.allocate(29); //Not sure about this, I think its right
		buffer.put(PID);
		buffer.putInt(seed);
		buffer.putInt(unknown);
		buffer.putInt(gamemode);
		buffer.putInt(eid);
		buffer.putFloat(spawnX);
		buffer.putFloat(spawnY);
		buffer.putFloat(spawnZ);
	}

	@Override
	public void decode(){}

	@Override
	public ByteBuffer getBuffer(){
		return buffer;
	}

	public Level getLevel(){
		return level;
	}
}
