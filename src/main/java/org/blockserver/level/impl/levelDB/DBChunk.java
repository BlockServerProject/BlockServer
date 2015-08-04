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
package org.blockserver.level.impl.levelDB;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.impl.Chunk;
import org.iq80.leveldb.DB;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * LevelDB Implemenation of a chunk.
 */
public class DBChunk extends Chunk{
	public DBChunk(ChunkPosition position, DB db){
		super(position, db);
	}

	@Override
	public void generate(){
		int offset = 0;
		for(int x = 0; x < 16; x++){
			for(int z = 0; z < 16; z++){
				blockIds[offset] = 0x02;
				for(int y = 0; y < 127; y++){
					blockIds[offset] = 0x00;
				}
			}
			offset++;
		}

		for(int i = 0; i < 16384; i++){
			blockLight[i] = 0x02;
		}
		for(int i = 0; i < 16384; i++){
			skylight[i] = 0x02;
		}
		for(int i = 0; i < 256; i++){
			biomeIds[i] = (byte) 0xFF;
		}

		ByteBuffer bb = ByteBuffer.allocate(1024);
		for(int i =0; i < 256; i++){
			bb.put((byte) 0x00);
			bb.put((byte) 0x85);
			bb.put((byte) 0xB2);
			bb.put((byte) 0x4A);
		}
		biomeColors = bb.array();
	}

	@Override
	public void load(){
		ByteBuffer key = ByteBuffer.allocate(9);
		key.order(ByteOrder.LITTLE_ENDIAN);
		key.putInt(chunkPosition.getX());
		key.putInt(chunkPosition.getZ());
		key.put(KeyDataType.TERRAIN_DATA.getByte());

		byte[] terrainData = database.get(key.array());

		blockIds = new byte[32768];
		blockData = new byte[16384];
		skylight = new byte[16384];
		blockLight = new byte[16384];
		biomeIds = new byte[256];
		biomeColors = new byte[1024];

		if(terrainData != null){
			ByteBuffer bb = ByteBuffer.wrap(terrainData);
			bb.get(blockIds);
			bb.get(blockData);
			bb.get(skylight);
			bb.get(blockLight);
			bb.get(biomeIds);
			bb.get(biomeColors);
		}else{
			System.out.println("[NOTICE]: Didn't find chunk data for chunk at " + chunkPosition.getX() + ", " + chunkPosition.getZ());
			generate();
			ByteBuffer bb = ByteBuffer.allocate(83200);
			bb.put(blockIds);
			bb.put(blockData);
			bb.put(skylight);
			bb.put(blockLight);
			bb.put(biomeIds);
			bb.put(biomeColors);

			database.put(key.array(), bb.array());
		}
	}

	@Override
	public void save(){
		ByteBuffer key = ByteBuffer.allocate(9);
		key.order(ByteOrder.LITTLE_ENDIAN);
		key.putInt(chunkPosition.getX());
		key.putInt(chunkPosition.getZ());
		key.put(KeyDataType.TERRAIN_DATA.getByte());

		ByteBuffer bb = ByteBuffer.allocate(83200);
		bb.put(blockIds);
		bb.put(blockData);
		bb.put(skylight);
		bb.put(blockLight);
		bb.put(biomeIds);
		bb.put(biomeColors);

		database.put(key.array(), bb.array());
	}
}
