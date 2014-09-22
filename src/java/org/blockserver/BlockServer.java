package org.blockserver;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.blockserver.chat.ChatManager;
import org.blockserver.chat.SimpleChatManager;
import org.blockserver.player.BSFPlayerDatabase;
import org.blockserver.player.PlayerDatabase;
import org.blockserver.utility.ConfigAgent;
import org.blockserver.utility.MinecraftVersion;

/**
 * <p>The default startup program to start the server.</p>
 * 
 * @author BlockServerProject
 */
public class BlockServer{
	public static final File CONFIG_FILE = new File(".", "config.txt"); // txt is easier for users to edit
	public static final File ADVANCED_CONFIG_FILE = new File(".", "advanced-config.properties"); // less likely to have users carelessly editing this
	public static void main(String[] args){
		File here = new File(".");
		if(!CONFIG_FILE.isFile()){
			generateConfig();
		}
		Properties config = ConfigAgent.loadConfig(CONFIG_FILE);
		Properties advancedConfig = ConfigAgent.loadConfig(ADVANCED_CONFIG_FILE);
		String serverName = config.getProperty("name");
		String ip = advancedConfig.getProperty("ip");
		short port = ConfigAgent.readShort(config, "port");
		int maxPlayers = ConfigAgent.readInt(config, "max-players");
		String motd = config.getProperty("motd");
		String defaultLevelName = config.getProperty("default-level");
		Class<? extends ChatManager> chatMgrType = null;
		Class<? extends PlayerDatabase> playerDbType = null;
		try{
			Class<?> chatMgr = ConfigAgent.readClass(advancedConfig, "chat manager class name");
			chatMgrType = chatMgr.asSubclass(ChatManager.class);
		}
		catch(ClassNotFoundException e){
			System.out.println("Chat manager type in advanced config is not found. Default (SimpleChatManager) will be used.");
			chatMgrType = SimpleChatManager.class;
		}
		catch(ClassCastException e){
			System.out.println("Chat manager type in advanced config must be subclass of ChatManager. Default (SimpleChatManager) will be used.");
			chatMgrType = SimpleChatManager.class;
		}
		try{
			Class<?> playerDb = ConfigAgent.readClass(advancedConfig, "player database class name");
			playerDbType = playerDb.asSubclass(PlayerDatabase.class);
		}
		catch(ClassNotFoundException e){
			System.out.println("Player database type in advanced config is not found. Default (BSFPlayerDatabase) will be used.");
			playerDbType = BSFPlayerDatabase.class;
		}
		catch(ClassCastException e){
			System.out.println("Player database type in advanced config must be subclass of PlayerDatabase. Default (BSFPlayerDatabase) will be used.");
			playerDbType = BSFPlayerDatabase.class;
		}
		File worldsDir = new File(here, "levels include path");
		File playersDir = new File(here, "players include path");
		try{
			Server server = new Server(serverName, ip, port, maxPlayers,
					MinecraftVersion.V095, motd, defaultLevelName, null, // TODO GenerationSettings
					chatMgrType, playerDbType,
					worldsDir, playersDir);
			server.run();
		}
		catch(SecurityException e){
			System.out.println("[CRITICAL] Server doesn't have permission to do the following and therefore crashed: " + e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	private static void generateConfig(){
		ConfigAgent.saveConfig(ConfigAgent.generateConfig(), CONFIG_FILE, "BlockServer config file for normal settings");
		ConfigAgent.saveConfig(ConfigAgent.getAdvancedConfig(), ADVANCED_CONFIG_FILE, "BlockServer config file for advanced settings: only edit these if you know what you are doing!");
	}
	public static boolean securityCheck(String ip, int port){
		String current = "do unknown operation";
		try{
			File file = new File(".");
			current = "get the server folder's canonical path";
			String path = null;
			try{
				path = file.getCanonicalPath();
				current = "read data from the server folder";
				SecurityManager mgr = System.getSecurityManager();
				mgr.checkRead(path);
				current = "edit the server folder";
				mgr.checkWrite(file.getName());
				current = "connet to the server socket";
				mgr.checkConnect(ip, port);
				return true;
			}
			catch(IOException e){
				e.printStackTrace();
				return false;
			}
		}
		catch(SecurityException e){
			System.out.println(String.format(Locale.US, "[EMERGENCY] Server doesn't have permission to %s. Stopping server.", current));
			System.exit(1);
			return false;
		}
	}
}
