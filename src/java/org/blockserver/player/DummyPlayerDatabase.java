package org.blockserver.player;

import org.blockserver.Server;
import org.blockserver.level.Level;

public class DummyPlayerDatabase implements PlayerDatabase{
	private Server server;

	public DummyPlayerDatabase(){
		server = Server.getInstance();
	}

	@Override
	public void init(){
		// allocate the memory :D
	}

	@Override
	public PlayerData load(String name){
		Level level = server.getDefaultLevel();
		return new PlayerData(level, level.getSpawnPos(), name);
	}

	@Override
	public void save(PlayerData data){
		// nothing to do, because this is in-memory database
	}

	@Override
	public boolean isAvailable(){
		return true;
	}

	@Override
	public void close(){
		// release the memory :D
	}
}
