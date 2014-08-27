package org.blockserver;

import org.blockserver.player.BinaryPlayerDatabase;
import org.blockserver.utility.MinecraftVersion;

/** The default startup program to start the server
 * 
 * @author BlockServerProject
 *
 */

public class BlockServer {
	public static void main(String[] args) {
	    /*
	    File root = new File(".");
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
                    BinaryPlayerDatabase.class);
            Server.setInstance(server);
            server.run();
        }
        catch(SecurityException e)
        {
            System.out.println("[CRITICAL] Server doesn't have permission to do the following and therefore crashed: " + e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        */
        Server server;
		try {
			server = new Server("BlockServer - A cool MCPE server written in java!", "0.0.0.0", 19132, 5, MinecraftVersion.V095, "level", BinaryPlayerDatabase.class);
	        server.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // TODO change the default player database

	}

}
