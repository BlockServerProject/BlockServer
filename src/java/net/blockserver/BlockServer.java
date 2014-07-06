package net.blockserver;

import net.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
        try {
            Server server = new Server("BlockServer - 0.05 Dev version, this is a TEST!", "0.0.0.0", 19132, 5, MinecraftVersion.V081);
            server.run();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

	}

}
