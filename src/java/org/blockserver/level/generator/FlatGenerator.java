package org.blockserver.level.generator;

import java.util.Random;

import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.LevelProvider;

@SuppressWarnings("unused")
public class FlatGenerator implements Generator{
	private LevelProvider provider;
	private long seed;
	private Random random;
	private int version;
	private String opts;

	public FlatGenerator(LevelProvider provider, long seed, Random random, int version, String opts){
		this.provider = provider;
		this.seed = seed;
		this.random = random;
		this.version = version;
		this.opts = opts;
	}
	public LevelProvider getProvider(){
		return provider;
	}
	@Override
	public void initLevel(){
		// TODO Auto-generated method stub
		
	}
	@Override
	public void generateChunk(ChunkPosition pos, int flag){
		// TODO Auto-generated method stub
		
	}
}
