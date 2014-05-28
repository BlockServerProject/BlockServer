package blockserver.core;
import java.net.*;

public class BlockServer {
	private Thread server;
	
	public BlockServer(int port) throws SocketException{
		BlockServerThread bst = new BlockServerThread(port);
		server = new Thread(bst, "BlockServer-Main");
	}

	public void start() {
		// TODO Auto-generated method stub
		server.start();
	}

}
