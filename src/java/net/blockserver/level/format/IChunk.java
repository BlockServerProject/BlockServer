package net.blockserver.level.format;

public interface IChunk
{
	int getX();
	void setX(int x);

	int getZ();
	void setZ(int z);

	int getBlockID(int x, int y, int z);
	void setBlockID(int x, int y, int z, int blockID);

	int getBlockMeta(int x, int y, int z);
	void  setBlockMeta(int x, int y, int z, int meta);

	int getBlockColor(int x, int y, int z);
	void setBlockColor(int x, int y, int z, int r, int g, int b);

	int getBlockSkyLight(int x, int y, int z);
	void setBlockSkyLight(int x, int y, int z, int level);

	int getBlockLight(int x, int y, int z);
	void setBlockLight(int x, int y, int z, int level);

	int getHighestBlockAt(int x, int z);

	int getBiomeId(int x, int z);
	void setBiomeId(int x, int z, int biomeID);

	int[] getBiomeColor(int x, int z);
	int setBiomeColor(int x, int z, int r, int g, int b);
}
