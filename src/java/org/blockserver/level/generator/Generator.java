package org.blockserver.level.generator;

import org.blockserver.level.format.LevelProvider;

public interface Generator{
	public void generate(LevelProvider provider, GenerationSettings settings);
}
