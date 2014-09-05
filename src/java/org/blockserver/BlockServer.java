package org.blockserver;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.blockserver.player.BSFPlayerDatabase;
import org.blockserver.utility.MinecraftVersion;

/**
 * <p>The default startup program to start the server.</p>
 * 
 * @author BlockServerProject
 */
public class BlockServer {
	public static void main(String[] args){
		String ip = "0.0.0.0";
		short port = 19132;
//		if(!securityCheck(ip, port)){
//			return;
//		}
		try{
			File here = new File(".");
			Server server = new Server("BlockServer - A cool MCPE server written in java!",
					ip, port, 5, MinecraftVersion.V095,
					"level", null, BSFPlayerDatabase.class, // TODO GenerationSettings
					new File(here, "worlds"), new File(here, "players"));
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
