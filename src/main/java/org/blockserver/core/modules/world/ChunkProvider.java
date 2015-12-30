package org.blockserver.core.modules.world;

/**
 * Written by Exerosis!
 */
public interface ChunkProvider {
    Chunk getChunkAt(int x, int y);
}