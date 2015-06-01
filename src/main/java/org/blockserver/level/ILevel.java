package org.blockserver.level;

import org.blockserver.utils.PositionDoublePrecision;

/**
 * Represents a Level.
 */
public abstract class ILevel{
	public static final int TIME_FULL = 24000;
	/**
	 * Load this level. May not load if already loaded.
	 * @throws LevelLoadException If there is an error while loading the level.
	 */
	public abstract void loadLevel() throws LevelLoadException;

	/**
	 * Save this level to disk, closes database. Must call load() after saving if you still want to do things.
	 * @throws LevelSaveException If there is an error while saving the level.
	 */
	public abstract void saveLevel() throws LevelSaveException;

	/**
	 * Get a chunk(column) at the corrdinates specified.
	 * @param x The X coordinate.
	 * @param z The Z coordinate.
	 * @return The chunk.
	 */
	public IChunk getChunkAt(int x, int z){
		return getChunkAt(new ChunkPosition(x, z));
	}
	public abstract IChunk getChunkAt(ChunkPosition pos);

	/**
	 * Unload a chunk(column) at the coordinates specified.
	 * @param x The X coordinate.
	 * @param z The Z coordinate.
	 * @return If the chunk exists and has been unloaded successfuly.
	 */
	public boolean unloadChunk(int x, int z){
		return unloadChunk(new ChunkPosition(x, z));
	}
	public abstract boolean unloadChunk(ChunkPosition pos);

	/**
	 * Chunk if a chunk is loaded.
	 * @param x The X coordinate
	 * @param z The Z coordinate
	 * @return Whether the chunk is loaded
	 */
	public boolean isChunkLoaded(int x, int z){
		return isChunkLoaded(new ChunkPosition(x, z));
	}
	public abstract boolean isChunkLoaded(ChunkPosition pos);

	/**
	 * Get this world's default spawn position.
	 * @return The Position.
	 */
	public abstract PositionDoublePrecision getSpawnPosition();

	/**
	 * Set this world's default spawn position.
	 */
	public abstract void setSpawnPosition(PositionDoublePrecision position);
}
