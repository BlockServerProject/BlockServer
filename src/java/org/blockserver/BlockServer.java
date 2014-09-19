package org.blockserver;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.blockserver.chat.SimpleChatManager;
import org.blockserver.player.BSFPlayerDatabase;
import org.blockserver.utility.MinecraftVersion;

/**
 * <p>The default startup program to start the server.</p>
 * 
 * @author BlockServerProject
 */
public class BlockServer{
	public static void main(String[] args){
		String serverName = "BlockServer - A cool MCPE server written in java!";
		String ip = "0.0.0.0";
		short port = 19132;
		int maxPlayers = 5;
		String defaultLevelName = "level";
		Class<? extends SimpleChatManager> chatMgrType = SimpleChatManager.class;
		Class<? extends BSFPlayerDatabase> playerDbType = BSFPlayerDatabase.class;
		File here = new File(".");
		File worldsDir = new File(here, "worlds");
		File playerDir = new File(here, "players");
		// TODO customization
		try{
			Server server = new Server(serverName, ip, port, maxPlayers,
					MinecraftVersion.V095, defaultLevelName, null, // TODO GenerationSettings
					chatMgrType, playerDbType,
					worldsDir, playerDir);
			server.run();
		}
		catch(SecurityException e){
			System.out.println("[CRITICAL] Server doesn't have permission to do the following and therefore crashed: " + e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
