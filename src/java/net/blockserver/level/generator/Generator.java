package net.blockserver.level.generator;

import java.util.Collections;
import java.util.Set;

public abstract class Generator<T extends Enum<?> & GenerationSettings> {

	protected Set<T> customSettings;
	protected Set<DefaultGenerationSettings> defaultGenerationSettings;

	public void generate(Set<DefaultGenerationSettings> defaultGenerationSettings){
		this.generate(defaultGenerationSettings, Collections.<T>emptySet());
	}

	public void generate(Set<DefaultGenerationSettings> defaultGenerationSettings, Set<T> customSettings){
		this.defaultGenerationSettings = defaultGenerationSettings;
		this.customSettings = customSettings;
	}

}
