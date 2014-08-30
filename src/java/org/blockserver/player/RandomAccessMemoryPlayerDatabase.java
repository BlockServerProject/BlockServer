package org.blockserver.player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.blockserver.level.Level;

public class RandomAccessMemoryPlayerDatabase extends PlayerDatabase{
	private Map<String, PlayerData> memory;

	@Override
	public void initialize(){
		memory = new HashMap<String, PlayerData>();
	}

	@Override
	public PlayerData loadPlayer(String name){
		if(memory.containsKey(name.toLowerCase(Locale.US))){
			return memory.get(name.toLowerCase(Locale.US));
		}
		Level level = getServer().getDefaultLevel();
		return new PlayerData(level, level.getSpawnPos(), name);
	}

	@Override
	public void savePlayer(PlayerData data){
		memory.put(data.getCaseName().toLowerCase(Locale.US), data);
	}
}
