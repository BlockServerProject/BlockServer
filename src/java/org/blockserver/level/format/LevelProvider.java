package org.blockserver.level.format;

import org.blockserver.Server;
import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public abstract class LevelProvider{
	private Level owner = null;
	private String ownerName;
	protected LevelProvider(String name){
		ownerName = name;
	}
	public abstract void init();
	public abstract boolean loadChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public abstract boolean saveChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public abstract boolean isChunkLoaded(ChunkPosition pos);
	public abstract boolean isChunkGenerated(ChunkPosition pos);
	public abstract boolean deleteChunk(ChunkPosition pos);
	public abstract Vector3d getSpawn();
	public abstract Server getServer();
	public final boolean cacheLevel(){
		return getLevel() instanceof Level;
	}
	public final Level getLevel(){
		if(owner == null){
			owner = getServer().getLevel(ownerName, false, false);
		}
		return owner;
	}
	public final String getLevelName(){
		try{
			return getLevel().getName();
		}
		catch(NullPointerException e){
			return null;
		}
	}
}
