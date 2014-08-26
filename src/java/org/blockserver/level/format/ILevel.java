package org.blockserver.level.format;

import org.blockserver.blocks.Block;

public interface ILevel extends IChunkManager
{
	String getName();
	void setName();

	String getPath();
	void setPath(String p);

	IChunk getChunk(int x, int z);
	void setChunk(int x, int z);

	Block getBlock(int x, int y, int z);
	void setBlock(Block block);

}
