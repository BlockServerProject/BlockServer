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
package org.blockserver.level.impl.dummy;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.IChunk;

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

	private ChunkPosition pos;

	public DummyChunk(ChunkPosition pos){
		this.pos = pos;
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

		for(int i = 0; i < 256; i++){
			biomeIds[i] = (byte) 0xFF;
		}
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
		return blocklight;
	}

	@Override
	public byte[] getBiomeIds(){
		return biomeIds;
	}

	@Override
	public byte[] getBiomeColors(){
		return biomeColors;
	}

	@Override
	public ChunkPosition getPosition(){
		return pos;
	}
}
