package org.blockserver.level.impl.dummy;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.LevelSaveException;
import org.blockserver.utils.PositionDoublePrecision;

/**
 * A Dummy implementation of a Level.
 */
public class DummyLevel extends ILevel{

	private PositionDoublePrecision spawnPosition;

	public DummyLevel(PositionDoublePrecision spawnPosition){
		this.spawnPosition = spawnPosition;
	}

	@Override
	public void loadLevel(){}

	@Override
	public void saveLevel() throws LevelSaveException{}

	@Override
	public IChunk getChunkAt(ChunkPosition pos){
		IChunk chunk = new DummyChunk(pos);
		chunk.generate();
		return chunk;
	}

	@Override
	public boolean unloadChunk(ChunkPosition pos){
		return true;
	}

	@Override
	public boolean isChunkLoaded(ChunkPosition pos){
		return true;
	}

	@Override
	public PositionDoublePrecision getSpawnPosition(){
		return spawnPosition;
	}

	@Override
	public void setSpawnPosition(PositionDoublePrecision position){
		spawnPosition = position;
	}
}
