package org.blockserver.level.generator;

import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.IChunk;

public interface Generator{
	public final static int FLAG_GENERATE_SPAWN =       0b00000001;
	public final static int FLAG_GENERATE_STANDBY =     0b00000010;
	public final static int FLAG_GENERATOR_USAGE =      0b00000100;

	public void generateChunk(ChunkPosition pos, IChunk chunk, int flag);
	public long getSeed();

	public abstract String getArgs();
}
