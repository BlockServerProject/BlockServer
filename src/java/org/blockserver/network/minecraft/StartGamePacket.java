package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.level.Level;

public class StartGamePacket implements BaseDataPacket {
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
	}
	
	public void encode(){
		seed = level.getSeed();
		unknown = 1;
		gamemode = level.getGamemode();
		spawnX = (float) level.getSpawnpos().getX();
		spawnY = (float) level.getSpawnpos().getY();
		spawnZ = (float) level.getSpawnpos().getZ();
		
		this.buffer = ByteBuffer.allocate(29); //Not sure about this, I think its right
		buffer.put(PID);
		buffer.putInt(seed);
		buffer.putInt(unknown);
		buffer.putInt(gamemode);
		buffer.putInt(eid);
		buffer.putFloat(spawnX);
		buffer.putFloat(spawnY);
		buffer.putFloat(spawnZ);
	}
	
	public void decode(){ };
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
