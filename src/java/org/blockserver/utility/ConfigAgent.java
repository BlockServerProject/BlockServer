package org.blockserver.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigAgent{
	public static Properties generateConfig(){
		Properties prop = new Properties();
		String serverName = "BlockServer - A cool MCPE server written in java!";
		short port = 19132;
		int maxPlayers = 5;
		String defaultLevelName = "level";
		String motd = "Harro! Welcome @p to @n!";
		prop.setProperty("name", serverName);
		prop.setProperty("port", Short.toString(port));
		prop.setProperty("max-players", Integer.toString(maxPlayers));
		prop.setProperty("default-level", defaultLevelName);
		prop.setProperty("motd", motd);
		return prop;
	}
	public static Properties getAdvancedConfig(){
		Properties prop = new Properties();
		String ip = "0.0.0.0";
		String chatMgrType = "org.blockserver.chat.SimpleChatManager";
		String playerDbType = "org.blockserver.player.BSFPlayerDatabase";
		String levelsDir = "levels";
		String playerDir = "players";
		prop.setProperty("ip", ip);
		prop.setProperty("chat-manager-class-name", chatMgrType);
		prop.setProperty("player-database-class-name", playerDbType);
		prop.setProperty("levels-include-path", levelsDir);
		prop.setProperty("players-include-path", playerDir);
		return prop;
	}
	public static void saveConfig(Properties prop, File file, String comments){
		// FileOutputStream will try to create the file if it doesn't exist.
		// FileNotFoundException is only thrown when it is unable to open, edit or create the file.
		try{
			prop.store(new FileOutputStream(file), comments);
		}
		catch(IOException e){
			throw new RuntimeException(e.getCause().getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public static Properties loadConfig(File file){
		if(!file.exists()){
			throw new RuntimeException("The file given does not exist!");
		}
		else{
			Properties prop = new Properties();
			try{
				prop.load(new FileInputStream(file));
			}
			catch(IOException e){
				throw new RuntimeException(e.getCause().getMessage());
			}
			finally{
				return prop;
			}
		}
	}
	public static File readFile(File here, Properties prop, String key){
		return new File(here, prop.getProperty(key));
	}
	public static Class<?> readClass(Properties prop, String key) throws ClassNotFoundException{
		return Class.forName(prop.getProperty(key));
	}
	public static int readInt(Properties prop, String key){
		return Integer.parseInt(prop.getProperty(key));
	}
	public static short readShort(Properties prop, String key){
		return Short.parseShort(prop.getProperty(key));
	}
}
