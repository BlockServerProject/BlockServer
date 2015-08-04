/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.level;

import org.blockserver.utils.PositionDoublePrecision;

/**
 * Represents a Level.
 */
public abstract class ILevel{
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
