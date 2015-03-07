package org.blockserver.level.impl;

import org.blockserver.level.IChunk;
import org.blockserver.utils.PositionDoublePrecision;
import org.iq80.leveldb.DB;

public abstract class Chunk implements IChunk{
	protected PositionDoublePrecision chunkPosition;
	protected byte[] blockIds;
	protected byte[] blockData;
	protected byte[] blockLight;
	protected byte[] skylight;

	protected byte[] biomeColors;
	protected byte[] biomeIds;

	protected DB database;

	public Chunk(PositionDoublePrecision position, DB db){
		chunkPosition = position;
		database = db;
	}

	public abstract void load();
	public abstract void save();

	@Override
	public PositionDoublePrecision getPosition(){
		return chunkPosition;
	}

	@Override
	public byte[] getBlockIds(){
		return blockIds;
	}

	@Override
	public byte[] getBlockData(){
		return blockData;
	}

	@Override
	public byte[] getSkylight(){
		return skylight;
	}

	@Override
	public byte[] getBlocklight(){
		return blockLight;
	}

	@Override
	public byte[] getBiomeIds(){
		return biomeIds;
	}

	@Override
	public byte[] getBiomeColors(){
		return biomeColors;
	}
}
