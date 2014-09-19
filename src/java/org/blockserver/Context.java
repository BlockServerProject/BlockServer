package org.blockserver;

/**
 * Represents a section in the server.
 * 
 * This interface also contains Minecraft-related miscellaneous constants.
 */
public interface Context extends GeneralConstants{
	public boolean isEnabled();
	public String getIdentifier();
}
