package org.blockserver.level.impl;

import org.blockserver.level.IChunk;
import org.blockserver.utils.Position;
import org.iq80.leveldb.DB;

/**
 * Created by jython234 on 2/8/2015.
 */
public abstract class Chunk implements IChunk{
    protected Position chunkPosition;
    protected byte[] blockIds;
    protected byte[] blockData;
    protected byte[] blockLight;
    protected byte[] skylight;

    protected byte[] biomeColors;
    protected byte[] biomeIds;

    protected DB database;

    public Chunk(Position position, DB db){
        chunkPosition = position;
        database = db;
    }

    public abstract void load();
    public abstract void save();

    @Override
    public Position getPosition() {
        return chunkPosition;
    }

    @Override
    public byte[] getBlockIds() {
        return blockIds;
    }

    @Override
    public byte[] getBlockData() {
        return blockData;
    }

    @Override
    public byte[] getSkylight() {
        return skylight;
    }

    @Override
    public byte[] getBlocklight() {
        return blockLight;
    }

    @Override
    public byte[] getBiomeIds() {
        return biomeIds;
    }

    @Override
    public byte[] getBiomeColors() {
        return biomeColors;
    }
}
