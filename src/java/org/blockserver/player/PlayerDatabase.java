package org.blockserver.player;

public interface PlayerDatabase {
    public PlayerData load(String name);
    public void save(PlayerData data);
    public boolean isAvailable();
    public void init();
    public void close();
}
