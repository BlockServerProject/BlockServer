package org.blockserver.command;

/**
 * 
 * @author Trent (SuperstarGamer)
 *
 */

public class Command {
	public static CommandType getCommandType(String Command) {
		CommandType commandType;
		switch (Command) {
			default:
				commandType = CommandType.UNKNOWN;
				break;
			case "say":
				commandType = CommandType.SAY;
				break;
			case "time":
				commandType = CommandType.TIME;
				break;
			case "save-all":
				commandType = CommandType.SAVE_ALL;
				break;
			case "save-on":
				commandType = CommandType.SAVE_ON;
				break;
			case "save-off":
				commandType = CommandType.SAVE_OFF;
				break;
			case "difficulty":
				commandType = CommandType.DIFFICULTY;
				break;
			case "toggledownfall":
				commandType = CommandType.TOGGLE_DOWNFALL;
				break;
			case "kick":
				commandType = CommandType.KICK;
				break;
			case "ban":
				commandType = CommandType.BAN;
				break;
			case "ban-ip":
				commandType = CommandType.BAN_IP;
				break;
			case "pardon":
				commandType = CommandType.PARDON;
				break;
			case "pardon-ip":
				commandType = CommandType.PARDON_IP;
				break;
			case "banlist":
				commandType = CommandType.BANLIST;
				break;
			case "whitelist":
				commandType = CommandType.WHITELIST;
				break;
			case "op":
				commandType = CommandType.OP;
				break;
			case "deop":
				commandType = CommandType.DEOP;
				break;
			case "teleport":
				commandType = CommandType.DEOP;
				break;
			case "give":
				commandType = CommandType.GIVE;
				break;
			case "gamemode":
				commandType = CommandType.GAMEMODE;
				break;
			case "defaultgamemode":
				commandType = CommandType.DEFAULT_GAMEMODE;
				break;
			case "spawpoint":
				commandType = CommandType.SPAWNPOINT;
				break;
			case "list":
				commandType = CommandType.LIST;
				break;
			case "help":
				commandType = CommandType.HELP;
				break;
			case "?":
				commandType = CommandType.ALT_HELP;
				break;
		}
		return commandType;
	}
}
