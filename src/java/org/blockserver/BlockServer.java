package org.blockserver;

import java.io.File;

import org.blockserver.player.BinaryPlayerDatabase;
import org.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
		try{
			File here = new File(".");
			Server server = new Server("BlockServer - A cool MCPE server written in java!",
					"0.0.0.0", 19132, 5, MinecraftVersion.V095, "level", BinaryPlayerDatabase.class,
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
}
