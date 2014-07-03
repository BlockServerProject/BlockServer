package net.blockserver;

import net.blockserver.utility.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
		Server server = new Server(19132, MinecraftVersion.V081, "BlockServer - 0.05 Dev version, this is a TEST!", 5);
		server.start();

	}

}
