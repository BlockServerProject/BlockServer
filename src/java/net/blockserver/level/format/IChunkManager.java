package net.blockserver.level.format;

public interface IChunkManager
{
	IChunk[] getAllChunks();
	IChunk getChunk(int x, int z);

	int getBlockID(int x, int y, int z);
	void setBlockID(int x, int y, int z, int blockID);

	int getBlockMeta(int x, int y, int z);
	void  setBlockMeta(int x, int y, int z, int meta);

	int getBlockColor(int x, int y, int z);
	void setBlockColor(int x, int y, int z, int r, int g, int b);

	boolean isChunkLoaded(int chunkX, int chunkZ);

}
