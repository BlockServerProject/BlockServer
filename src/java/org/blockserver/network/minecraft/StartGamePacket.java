package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public class StartGamePacket implements BaseDataPacket{
	protected ByteBuffer buffer;
	public byte PID = PacketsID.START_GAME;
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
		this.spawnX = (float) spawnpos.getX();
		this.spawnY = (float) spawnpos.getY();
		this.spawnZ = (float) spawnpos.getZ();
		this.gamemode = gamemode;
		this.seed = seed;
		this.eid = eid;
	}

	@Override
	public void encode(){
		buffer = ByteBuffer.allocate(29); //Not sure about this, I think its right
		buffer.put(PID);
		buffer.putInt((int) seed);
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
}
