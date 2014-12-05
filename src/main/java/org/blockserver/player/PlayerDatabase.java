package org.blockserver.player;

import org.blockserver.Server;

import com.sun.istack.internal.NotNull;

public abstract class PlayerDatabase{
	private Server server = null;
	public void init(Server server){
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
	@NotNull
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
	public void validate() throws IllegalStateException{
		if(!(server instanceof Server)){
			throw new IllegalStateException("Database not initialized");
		}
	}
}
