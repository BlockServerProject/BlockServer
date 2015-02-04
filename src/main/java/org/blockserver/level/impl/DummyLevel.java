package org.blockserver.level.impl;

import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.utils.Position;

/**
 * A Dummy implementation of a Level.
 */
public class DummyLevel implements ILevel{

    private Position spawnPosition;

    public DummyLevel(Position spawnPosition){
        this.spawnPosition = spawnPosition;
    }

    public IChunk getChunkAt(int x, int z){
        return new DummyChunk(x, z);
    }

    public Position getSpawnPosition(){
        return spawnPosition;
    }
}
