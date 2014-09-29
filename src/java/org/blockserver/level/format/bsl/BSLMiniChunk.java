package org.blockserver.level.format.bsl;

import org.blockserver.level.format.IMiniChunk;
import org.blockserver.utility.Utils;

public class BSLMiniChunk implements IMiniChunk{
	private byte[] blocks, damages, blockLights, skyLights;

	public BSLMiniChunk(byte[] blocks, byte[] damages, byte[] blockLights, byte[] skyLights){
		this.blocks = blocks;
		this.damages = damages;
		this.blockLights = blockLights;
		this.skyLights = skyLights;
	}

	@Override
	public byte getBlockID(byte x, byte y, byte z){
		return blocks[y << 8 + z << 4 + x];
	}
	@Override
	public void setBlockID(byte x, byte y, byte z, byte id){
		blocks[y << 8 + z << 4 + x] = id;
	}

	@Override
	public byte getBlockDamage(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, damages);
	}
	@Override
	public void setBlockDamage(byte x, byte y, byte z, byte damage){
		Utils.setNibble(x, y, z, damage, damages);
	}

	@Override
	public byte getSkyLight(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, skyLights);
	}
	@Override
	public void setSkyLight(byte x, byte y, byte z, byte lightLevel){
		Utils.setNibble(x, y, z, lightLevel, skyLights);
	}

	@Override
	public byte getBlockLight(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, blockLights);
	}
	@Override
	public void setBlockLight(byte x, byte y, byte z, byte blockLevel){
		Utils.setNibble(x, y, z, blockLevel, blockLights);
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
}
