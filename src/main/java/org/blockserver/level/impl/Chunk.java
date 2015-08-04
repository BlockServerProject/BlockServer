/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.level.impl;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.IChunk;
import org.iq80.leveldb.DB;

public abstract class Chunk implements IChunk{
	protected ChunkPosition chunkPosition;
	protected byte[] blockIds;
	protected byte[] blockData;
	protected byte[] blockLight;
	protected byte[] skylight;

	protected byte[] biomeColors;
	protected byte[] biomeIds;

	protected DB database;

	public Chunk(ChunkPosition position, DB db){
		chunkPosition = position;
		database = db;
	}

	public abstract void load();
	public abstract void save();

	@Override
	public ChunkPosition getPosition(){
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
