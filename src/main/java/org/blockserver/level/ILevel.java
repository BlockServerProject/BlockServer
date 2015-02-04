package org.blockserver.level;

import org.blockserver.utils.Position;

/**
 * Represents a Level.
 */
public interface ILevel {

    IChunk getChunkAt(int x, int z);

    Position getSpawnPosition();
}
