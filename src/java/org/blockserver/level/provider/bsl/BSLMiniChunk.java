package org.blockserver.level.provider.bsl;

import org.blockserver.level.provider.ChunkPosition.MiniChunkPosition;
import org.blockserver.utility.Utils;

public class BSLMiniChunk{
	private byte[] blocks, damages, blockLights, skyLights, biomes;
	private int[] biomeColors;
	private MiniChunkPosition pos;

	public BSLMiniChunk(MiniChunkPosition position, byte[] blocks, byte[] damages, byte[] blockLights, byte[] skyLights, byte[] biomes, byte[] biomeColors){
		pos = position;
		this.blocks = blocks;
		this.damages = damages;
		this.blockLights = blockLights;
		this.skyLights = skyLights;
		this.biomes = biomes;
		/*
		ByteBuffer bb = ByteBuffer.wrap(biomeColors);
		for(int i = 0; i < 256; i++){
			this.biomeColors[i] = bb.getInt();
		}
		*/
	}

	public byte getBlockID(byte x, byte y, byte z){
		return blocks[x << 8 + z << 4 + y];
	}
	public void setBlockID(byte x, byte y, byte z, byte id){
		blocks[x << 8 + z << 4 + y] = id;
	}

	public byte getBlockDamage(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, damages);
	}
	public void setBlockDamage(byte x, byte y, byte z, byte damage){
		Utils.setNibble(x, y, z, damage, damages);
	}

	public byte getSkyLight(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, skyLights);
	}
	public void setSkyLight(byte x, byte y, byte z, byte lightLevel){
		Utils.setNibble(x, y, z, lightLevel, skyLights);
	}

	public byte getBlockLight(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, blockLights);
	}
	public void setBlockLight(byte x, byte y, byte z, byte blockLevel){
		Utils.setNibble(x, y, z, blockLevel, blockLights);
	}

	public byte getBiome(byte x, byte z){
		return biomes[(z << 4) + x];
	}
	public void setBiome(byte x, byte z, byte biome){
		biomes[(z << 4) + x] = biome;
	}

	public int getBiomeColor(byte x, byte z){
		return biomeColors[(z << 4) + x];
	}
	public void setBiomeColor(byte x, byte z, int biomeColor){
		biomeColors[(z << 4) + x] = biomeColor;
	}

	public byte[] getBlocks(){
		return blocks;
	}
	public byte[] getDamages(){
		return damages;
	}
	public byte[] getBlockLights(){
		return blockLights;
	}
	public byte[] getSkyLights(){
		return skyLights;
	}
	public byte[] getBiomes(){
		return biomes;
	}
	public byte[] getBiomeColors(){
		return new byte[0x400];
		/*
		ByteBuffer bb = ByteBuffer.allocate(0x400);
		for(int i: biomeColors){
			bb.putInt(i);
		}
		return bb.array();
		*/
	}

	public MiniChunkPosition getPosition(){
		return pos;
	}
}
