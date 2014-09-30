package org.blockserver.level.format;

import org.blockserver.math.Vector3d;

public interface LevelProvider{
	public void init();
	public boolean loadChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public boolean saveChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public boolean isChunkLoaded(ChunkPosition pos);
	public boolean isChunkGenerated(ChunkPosition pos);
	public boolean deleteChunk(ChunkPosition pos);
	public Vector3d getSpawn();
}
