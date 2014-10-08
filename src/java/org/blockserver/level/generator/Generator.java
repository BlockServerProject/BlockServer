package org.blockserver.level.generator;

public interface Generator{
	public final static int FLAG_GENERATE_SPAWN =       0b00000001;
	public final static int FLAG_GENERATE_STANDBY =     0b00000010;
	public final static int FLAG_GENERATOR_USAGE =      0b00000100;

	public void initLevel();
	public void generateChunk(int flag);
}
