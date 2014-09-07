package org.blockserver;

/**
 * Represents a section in the server.
 * 
 * This interface also contains Minecraft-related miscellaneous constants.
 */
public interface Context{
	/**
	 * Default integer size of player inventory.
	 */
	public final static int DEFAULT_PLAYER_INVENTORY_SIZE = 36;

	public boolean isEnabled();
	public String getIdentifier();
}
