package org.blockserver.player;

public interface PlayerDatabase {
	public void init();
	public PlayerData load(String name);
	public void save(PlayerData data);
	public boolean isAvailable();
	public void close();
}
