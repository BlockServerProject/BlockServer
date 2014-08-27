package org.blockserver.level.format;

import org.blockserver.blocks.Block;
import org.blockserver.math.Vector3;

public interface ILevel extends IChunkManager{
	public String getName();
	public String getPath();

	public IChunk getChunk(int x, int z);
	public void setChunk(int x, int z, IChunk chunk);

	public Block getBlock(int x, int y, int z);
	public void setBlock(Vector3 coords, Block block);
}
