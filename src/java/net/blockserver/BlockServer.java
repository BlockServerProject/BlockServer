package net.blockserver;

import java.io.File;

import net.blockserver.player.DummyPlayerDatabase;
import net.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
	    File root = new File(".") //Make this fail, testing travis
	    String path;
	    SecurityManager secur = System.getSecurityManager();
	    try{
	        path = root.getAbsolutePath();
	        secur.checkRead(path);
	        try{
	            secur.checkWrite(path);
	        }
	        catch(SecurityException e){
	            System.out.println("[CRITICAL] Server doesn't have permission to edit the server folder!");
	            return;
	        }
	    }
	    catch(SecurityException e){
	        System.out.println("[CRITICAL] Server doesn't have permission to read the server folder!");
	        return;
	    }
        try {
            Server server = new Server("BlockServer - A cool MCPE server written in java!",
                    "0.0.0.0", 19132, 5, MinecraftVersion.V095, "level",
                    DummyPlayerDatabase.class); // TODO change the default player database
            server.run();
        }
        catch(SecurityException e)
        {
            System.out.println("[CRITICAL] Server doesn't have permission to do the following and crashed: " + e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

	}

}
