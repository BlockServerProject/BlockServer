package org.blockserver.cmd;

/**
 * 
 * @author Trent (SuperstarGamer)
 *
 */
public enum CommandType {
	
	// Server Related
	SAY,
	TIME,
	SAVE_ALL, SAVE_ON, SAVE_OFF,
	DIFFICULTY,
	TOGGLE_DOWNFALL,
	
	// Operator Related
	KICK, BAN, BAN_IP, PARDON, PARDON_IP,
	BANLIST, WHITELIST,
	OP, DEOP,
	
	// Player Related
	TELEPORT,
	GIVE,
	GAMEMODE, DEFAULT_GAMEMODE,
	SPAWNPOINT,
	
	// Other
	LIST, HELP, ALT_HELP,
	UNKNOWN
	
}
