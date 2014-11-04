package org.blockserver.level.provider;

import java.io.File;

import org.blockserver.Server;

public abstract class LevelProviderType<T extends LevelProvider>{
	public abstract Class<T> getProviderClass();
	public String getName(){
		return getProviderClass().getCanonicalName();
	}
	public abstract boolean supports(File worldDir);
	public abstract T instantiate(Server server, File worldDir, String name, long seed);
}
