/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.player;

import org.blockserver.Server;

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
	public abstract PlayerData readPlayer(Player player);
	/**
	 * Saves data of a player into the database.
	 * @param data A {@link PlayerData} object representing the data to save
	 * @return {@code true} if data are saved, {@code false} otherwise.
	 */
	public abstract boolean savePlayer(PlayerData data);
	public PlayerData dummy(Player player){
		PlayerData data = new PlayerData();
		data.player = player;
		return data;
	}
	public void validate() throws IllegalStateException{
		if(server == null){
			throw new IllegalStateException("Database not initialized");
		}
	}
}
