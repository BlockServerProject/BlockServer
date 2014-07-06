package net.blockserver;

import net.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
        try {
            Server server = new Server("BlockServer - A Server", "0.0.0.0", 19135, 5, MinecraftVersion.V081);
            server.run();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

	}

}
