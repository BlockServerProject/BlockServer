package org.blockserver.level.impl.dummy;

import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.impl.dummy.DummyChunk;
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
        IChunk chunk = new DummyChunk(x, z);
        chunk.generate();
        return chunk;
    }

    public Position getSpawnPosition(){
        return spawnPosition;
    }
}
