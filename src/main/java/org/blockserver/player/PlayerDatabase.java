package org.blockserver.player;

import org.blockserver.Server;

public abstract class PlayerDatabase{
	private Server server;
	public PlayerDatabase(Server server){
		this.server = server;
	}
	public Server getServer(){
		return server;
	}
	/**
	 * Reads data of a player from the database.
	 * @param player player to check
	 * @return A {@link PlayerData} object representing the data
	 */
	public abstract PlayerData readPlayer(Player player);
	/**
	 * Saves data of a player into the database.
	 * @param data A {@link PlayerData} object representing the data to save
	 * @return <code>true</code> if data are saved, <code>false</code> otherwise.
	 */
	public abstract boolean savePlayer(PlayerData data);
	public PlayerData dummy(Player player){
		PlayerData data = new PlayerData();
		data.player = player;
		return data;
	}
}
