package org.blockserver.level.format;

public interface IChunkManager{
	public IChunk[] getAllChunks();
	public IChunk getChunk(int x, int z);
	public boolean isChunkLoaded(int chunkX, int chunkZ);

	public int getBlockID(int x, int y, int z);
	public void setBlockID(int x, int y, int z, int blockID);

	public int getBlockMeta(int x, int y, int z);
	public void setBlockMeta(int x, int y, int z, int meta);

	public int getBlockColor(int x, int y, int z);
	public void setBlockColor(int x, int y, int z, int r, int g, int b);
}
