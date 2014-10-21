package org.blockserver.level.provider;

import java.util.Collection;
import java.util.Map;

import org.blockserver.GeneralConstants;
import org.blockserver.entity.SavedEntity;
import org.blockserver.utility.Utils;

public abstract class IChunk implements GeneralConstants{
	public abstract Map<Integer, SavedEntity> getMappedEntities();
	public abstract Collection<SavedEntity> getEntities();
	public abstract byte[] getBlocks();
	public abstract byte[] getDamages();
	public abstract byte[] getSkyLights();
	public abstract byte[] getBlockLights();
	public abstract byte[] getBiomeIds();
	public abstract int[] getBiomeColors();
	public abstract int getX();
	public abstract int getZ();
	public abstract byte[] getTiles();
	public byte getBlock(byte x, byte y, byte z){
		x &= 0x0F;
		y &= 0x0F;
		z &= 0x0F;
		return getBlocks()[(x << 11) + (z << 7) + y];
	}
	public byte getDamages(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, getDamages());
	}
	public byte getSkyLight(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, getSkyLights());
	}
	public byte getBlockLight(byte x, byte y, byte z){
		return Utils.getNibble(x, y, z, getBlockLights());
	}
	public byte getBiomeId(byte x, byte z){
		return getBiomeIds()[(x << 4) + z];
	}
	public int getBiomeColor(byte x, byte z){
		return getBiomeColors()[(x << 4) + z];
	}
}
