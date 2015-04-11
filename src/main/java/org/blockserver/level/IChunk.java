package org.blockserver.level;

/**
 * Represents a 16x16x128 Chunk.
 */
public interface IChunk{
	public void generate();
	public ChunkPosition getPosition();

	public byte[] getBlockIds();
	public byte[] getBlockData();
	public byte[] getSkylight();
	public byte[] getBlocklight();
	public byte[] getBiomeIds();
	public byte[] getBiomeColors();
	//TODO: Add tile entities
}
