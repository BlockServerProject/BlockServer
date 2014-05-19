package blockserver;
import java.net.InetAddress;
import java.net.SocketException;

import blockserver.core.BlockServer;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockServer server = null;
		try {
			server = new BlockServer(19132);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.start();

	}

}
