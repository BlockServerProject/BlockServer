package blockserver.core;
import java.net.*;
import java.io.*;
import java.util.*;

public class BlockServer {
	private BlockServerThread server;
	
	public BlockServer(int port) throws SocketException{
		server = new BlockServerThread(port);
	}

	public void start() {
		// TODO Auto-generated method stub
		server.start();
	}

}
