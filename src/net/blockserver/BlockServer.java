package net.blockserver;

import net.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
		Server server = new Server(19132, MinecraftVersion.V081, "Test", 5);
		server.start();

	}

}
