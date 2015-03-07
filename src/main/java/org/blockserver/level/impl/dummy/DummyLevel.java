package org.blockserver.level.impl.dummy;

import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.LevelSaveException;
import org.blockserver.utils.PositionDoublePrecision;

/**
 * A Dummy implementation of a Level.
 */
public class DummyLevel implements ILevel{

	private PositionDoublePrecision spawnPosition;

	public DummyLevel(PositionDoublePrecision spawnPosition){
		this.spawnPosition = spawnPosition;
	}

	@Override
	public void loadLevel(){

	}

	@Override
	public void saveLevel() throws LevelSaveException{

	}

	@Override
	public IChunk getChunkAt(int x, int z){
		IChunk chunk = new DummyChunk(x, z);
		chunk.generate();
		return chunk;
	}

	@Override
	public boolean unloadChunk(int x, int z){
		return true;
	}

	@Override
	public PositionDoublePrecision getSpawnPosition(){
		return spawnPosition;
	}

	@Override
	public void setSpawnPosition(PositionDoublePrecision position){
		this.spawnPosition = position;
	}
}
