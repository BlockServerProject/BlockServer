package org.blockserver.command;

public class CommandType{
	
	// Server Related
	/** Usage: say <message> **/
	public static String SAY = "say";
	
	/** Usage: time <set|add> <number> **/
	public static String TIME = "time";
	
	/** Usage: save-all **/
	public static String SAVE_ALL = "save-all";
	
	/** Usage: save-on **/
	public static String SAVE_ON = "save-on";
	
	/** Usage: save-off **/
	public static String SAVE_OFF = "save-off";
	
	/** Usage: difficulty <difficulty> **/
	public static String DIFFICULTY = "difficulty";
	
	/** Usage: toggledownfall **/
	public static String TOGGLE_DOWNFALL = "toggledownfall";
	
	/** Usage: stop **/
	public static String STOP = "stop";
	
	
	// Operator Related
	/** Usage: kick <player> **/
	public static String KICK = "kick";
	
	/** Usage: ban <player> **/
	public static String BAN = "ban";
	
	/** Usage: ban-ip <address> **/
	public static String BAN_IP = "ban-ip";
	
	/** Usage: banlist **/
	public static String BAN_LIST = "banlist";
	
	/** Usage: pardon <player> **/
	public static String PARDON = "pardon";
	
	/** Usage: pardon <address> **/
	public static String PARDON_IP = "pardon-ip";
	
	/** Usage #1: whitelist <add|remove> <playername> 
	 *  Usage #2: whitelist list 
	 *  Usage #3: whitelist <on|off> 
	 *  Usage #4: whitelist reload
	 **/
	public static String WHITELIST = "whitelist";
	
	/** Usage: op <player> **/
	public static String OP = "op";
	
	/** Usage: deop <player> **/
	public static String DEOP = "deop";
	
	// Player Related
	/** Usage #1: tp <targetplayer> 
	 *  Usage #2: tp <playername> <targetplayer>
	 *  Usage #3: tp <playername> <x> <y> <z>
	 **/
	public static String TELEPORT = "tp";
	
	/** Usage: give <player> **/
	public static String GIVE = "give";
	
	/** Usage: gamemode <id> [player] **/
	public static String GAMEMODE = "gamemode";
	
	/** Usage: defaultgamemode <id> **/
	public static String DEFAULT_GAMGMODE = "defaultgamemode";
	
	/** Usage: spawnpoint <player> <x> <y> <z>**/
	public static String spawnpoint = "spawnpoint";
	
	// Other
	/** Usage: list **/
	public static String LIST = "list";
	
	/** Usage: help **/
	public static String HELP = "help";
	
}
