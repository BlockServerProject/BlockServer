package org.blockserver.level.generator;

import org.blockserver.level.provider.LevelProvider;

public interface Generator{
	public void generate(LevelProvider provider, GenerationSettings settings);
}
