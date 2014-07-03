import blockserver.MinecraftPEServer;
import blockserver.MinecraftVersion;

public class BlockServer {
	public static void main(String[] args) {
		MinecraftPEServer server = new MinecraftPEServer(19132, MinecraftVersion.V081, "Test", 5);
		server.start();

	}

}
