package org.blockserver.level.provider;

import org.blockserver.Server;
import org.blockserver.level.Level;
import org.blockserver.level.generator.Generator;
import org.blockserver.math.Vector3d;
import org.blockserver.utility.Gettable;

public abstract class LevelProvider{
	private Level owner = null;
	private String ownerName;
	protected LevelProvider(String name){
		ownerName = name;
	}

	/**
	 * This method loads the level if it exists.<br>
	 * If it doesn't, it would attempt to generate a level using the generator passed.
	 * If the generator is null, it would be replaced by the default generator by {@link Server#getDefaultLevelGenerationSettings()}.
	 */
	public abstract void init(Gettable<Generator> generator) throws LevelCorruptedException;
	public abstract boolean loadChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public abstract IChunk getChunk(ChunkPosition pos);
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
