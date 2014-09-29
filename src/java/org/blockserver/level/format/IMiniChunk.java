package org.blockserver.level.format;

public interface IMiniChunk{
	public byte getBlockID(byte x, byte y, byte z);
	public void setBlockID(byte x, byte y, byte z, byte id);
	public byte getBlockDamage(byte x, byte y, byte z);
	public void setBlockDamage(byte x, byte y, byte z, byte damage);
	public byte getSkyLight(byte x, byte y, byte z);
	public void setSkyLight(byte x, byte y, byte z, byte lightLevel);
	public byte getBlockLight(byte x, byte y, byte z);
	public void setBlockLight(byte x, byte y, byte z, byte blockLevel);
}
