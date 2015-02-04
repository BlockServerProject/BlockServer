package org.blockserver.level;

import org.blockserver.utils.Position;

/**
 * Represents a 16x16x128 Chunk.
 */
public interface IChunk {

    void generate();
    Position getPosition();

    byte[] getBlockIds();
    byte[] getBlockData();
    byte[] getSkylight();
    byte[] getBlocklight();

    byte[] getBiomeIds();
    byte[] getBiomeColors();

    //TODO: Add tile entities
}
