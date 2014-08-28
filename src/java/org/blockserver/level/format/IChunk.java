package org.blockserver.level.format;

public interface IChunk{
	public int getX();
	public void setX(int x);

	public int getZ();
	public void setZ(int z);

	public int getBlockID(int x, int y, int z);
	public void setBlockID(int x, int y, int z, int blockID);

	public int getBlockMeta(int x, int y, int z);
	public void setBlockMeta(int x, int y, int z, int meta);

	public int getBlockColor(int x, int y, int z);
	public void setBlockColor(int x, int y, int z, int r, int g, int b);

	public int getBlockSkyLight(int x, int y, int z);
	public void setBlockSkyLight(int x, int y, int z, int level);

	public int getBlockLight(int x, int y, int z);
	public void setBlockLight(int x, int y, int z, int level);

	public int getHighestBlockAt(int x, int z);

	public int getBiomeId(int x, int z);
	public void setBiomeId(int x, int z, int biomeID);

	public int[] getBiomeColor(int x, int z);
	public int setBiomeColor(int x, int z, int r, int g, int b);
}
