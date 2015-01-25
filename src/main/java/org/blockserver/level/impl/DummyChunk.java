package org.blockserver.level.impl;

import org.blockserver.level.IChunk;
import org.blockserver.utils.Position;

/**
 * A Dummy implementation of a Chunk.
 */
public class DummyChunk implements IChunk{

    private byte[] blockIds = new byte[32768];
    private byte[] blockData = new byte[16384];
    private byte[] skylight = new byte[16384];
    private byte[] blocklight = new byte[16384];

    private byte[] biomeIds = new byte[256];
    private byte[] biomeColors = new byte[1024];

    private Position pos;

    public DummyChunk(int x, int z){
        pos = new Position(x, 0, z);
    }

    public void generate(){
        //Dummy
    }

    public byte[] getBlockIds(){
        return blockIds;
    }

    public byte[] getBlockData(){
        return blockData;
    }

    public byte[] getSkylight(){
        return skylight;
    }

    public byte[] getBlocklight(){
        return blocklight;
    }

    public byte[] getBiomeIds(){
        return biomeIds;
    }

    public byte[] getBiomeColors(){
        return biomeColors;
    }

    public Position getPosition(){
        return pos;
    }
}
