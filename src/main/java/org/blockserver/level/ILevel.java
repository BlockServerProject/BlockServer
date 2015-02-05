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
     * Get a chunk(column) at the corrdinates specified.
     * @param x The X coordinate.
     * @param z The Z coordinate.
     * @return The chunk.
     */
    IChunk getChunkAt(int x, int z);

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
