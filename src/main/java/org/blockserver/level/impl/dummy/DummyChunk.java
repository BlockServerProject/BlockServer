package org.blockserver.level.impl.dummy;

import org.blockserver.level.IChunk;
import org.blockserver.utils.PositionDoublePrecision;

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

	private PositionDoublePrecision pos;

	public DummyChunk(int x, int z){
		pos = new PositionDoublePrecision(x, 0, z);
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
	public PositionDoublePrecision getPosition(){
		return pos;
	}
}
