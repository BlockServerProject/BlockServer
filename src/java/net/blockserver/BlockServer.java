package net.blockserver;

import net.blockserver.network.raknet.ACKPacket;
import net.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
        try {
            Server server = new Server("BlockServer - A cool MCPE server written in java!", "0.0.0.0", 19132, 5, MinecraftVersion.V90);
            server.run();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

	}

}
