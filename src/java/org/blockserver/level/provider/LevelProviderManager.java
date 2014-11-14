package org.blockserver.level.provider;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.blockserver.Server;

public class LevelProviderManager{
	private Server server;
	private Map<String, LevelProviderType<?>> types;
	public LevelProviderManager(Collection<LevelProviderType<?>> types, Server server){
		this.server = server;
		this.types = new HashMap<String, LevelProviderType<?>>(types.size());
	}
	public LevelProvider filter(File folder, String name, long seed){
		for(LevelProviderType<?> type: types.values()){
			if(type.supports(folder)){
				return type.instantiate(server, folder, name, seed);
			}
		}
		return null;
	}
}
