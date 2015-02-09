package org.blockserver.level;

import org.blockserver.utils.Position;

/**
 * Represents a Level.
 */
public interface ILevel {
    /**
     * Load this level. May not load if already loaded.
     * @throws LevelLoadException If there is an error while loading the level.
     */
    void loadLevel() throws LevelLoadException;

    /**
     * Save this level to disk, closes database. Must call load() after saving if you still want to do things.
     * @throws LevelSaveException If there is an error while saving the level.
     */
    void saveLevel() throws LevelSaveException;

    /**
     * Get a chunk(column) at the corrdinates specified.
     * @param x The X coordinate.
     * @param z The Z coordinate.
     * @return The chunk.
     */
    IChunk getChunkAt(int x, int z);

    /**
     * Unload a chunk(column) at the coordinates specified.
     * @param x The X coordinate.
     * @param z The Z coordinate.
     * @return If the chunk was unloaded successfuly.
     */
    boolean unloadChunk(int x, int z);

    /**
     * Get this world's default spawn position.
     * @return The Position.
     */
    Position getSpawnPosition();

    /**
     * Set this world's default spawn position.
     */
    void setSpawnPosition(Position position);
}
