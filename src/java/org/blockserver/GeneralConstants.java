package org.blockserver;

public interface GeneralConstants{
	/**
	 * Default integer size of player inventory.
	 */
	public final static int DEFAULT_PLAYER_INVENTORY_SIZE = 36;
	public final static int WORLD_HEIGHT = 0x80;
	public final static int WORLD_HIGHEST_BLOCK = WORLD_HEIGHT - 1;
	public final static byte WORLD_MINICHUNK_CNT = WORLD_HEIGHT >> 4;
}
