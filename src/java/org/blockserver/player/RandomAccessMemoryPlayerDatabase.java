package org.blockserver.player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RandomAccessMemoryPlayerDatabase extends PlayerDatabase{
	private Map<String, PlayerData> memory;

	@Override
	public void initialize(){
		memory = new HashMap<String, PlayerData>();
	}

	@Override
	public PlayerData loadPlayer(Player player, String name){
		return dummy(player, name);
	}

	@Override
	public void savePlayer(PlayerData data){
		memory.put(data.getCaseName().toLowerCase(Locale.US), data);
	}
}
