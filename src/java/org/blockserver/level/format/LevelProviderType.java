package org.blockserver.level.format;

import java.io.File;

public abstract class LevelProviderType<T extends LevelProvider>{
	public abstract Class<T> getProviderClass();
	public String getName(){
		return getProviderClass().getCanonicalName();
	}
	public abstract boolean isValid(File worldDir);
	public abstract T instantiate(File worldDir);
}
