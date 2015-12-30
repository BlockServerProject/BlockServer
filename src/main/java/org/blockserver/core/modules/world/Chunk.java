package org.blockserver.core.modules.world;

/**
 * Written by Exerosis!
 */
public interface Chunk {
    Block getBlockAt(int x, int y, int z);
}