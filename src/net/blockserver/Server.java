package net.blockserver;

import net.blockserver.network.BasePacketHandler;
import net.blockserver.network.PacketHandler081;
import net.blockserver.utility.LoggerFormatter;
import net.blockserver.utility.MinecraftVersion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Server extends Thread {
	public Logger serverLog = Logger.getLogger(Server.class.getName());
	public MinecraftVersion MCVERSION;
	public boolean serverRunning;
	public long startTime;
	public long serverID;
	
	protected int serverPort;
	protected String serverName;
	protected int maxPlayers;
	protected int mtu = 512;
	
	private DatagramSocket networkSocket;
	
	public Server(int port, MinecraftVersion version, String name, int players){
		//Set up server data
		startTime = System.currentTimeMillis();
		serverPort = port;
		serverName = name;
		maxPlayers= players;
		MCVERSION = version;
		Random r = new Random();
		serverID = r.nextLong();
		
		//Set up our logger
		Logger rootLogger = Logger.getLogger("");
	    Handler[] handlers = rootLogger.getHandlers();
	    if (handlers[0] instanceof ConsoleHandler) {
	      rootLogger.removeHandler(handlers[0]);
	    }
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LoggerFormatter());
		serverLog.addHandler(handler);
	}
	
	public void run(){
		try {
			serverLog.info("Starting server on: *:"+serverPort+", implementing "+MinecraftVersion.versionToString(MCVERSION));
			networkSocket = new DatagramSocket(serverPort);
			serverRunning = true;
			handlePackets();
		} catch (SocketException e) {
			serverLog.severe("COULD NOT BIND TO PORT - Maybe another server is running on "+serverPort+"?");
			serverLog.severe("Shutting down server due to error.");
			System.exit(1);
		} catch (IOException e) {
			int time = (int) (System.currentTimeMillis() - startTime);
			serverLog.warning("IOException at: " + time + " ms");
		}
		
	}
	
	private void handlePackets() throws IOException{
		BasePacketHandler handler = null;
		if(MCVERSION == MinecraftVersion.V081){
			handler = new PacketHandler081(networkSocket, this);
		}
		else if(MCVERSION == MinecraftVersion.V090){
			//TODO: Implement Networking for 0.9.0
			serverLog.warning("Networing for 0.9.0 is not implemented yet!");
			serverLog.info("Shutting down server due to Networking Not Implemented.");
			System.exit(1);
		}
		while(serverRunning){
			byte[] byteBuf = new byte[mtu];
			DatagramPacket packet = new DatagramPacket(byteBuf, byteBuf.length);
			networkSocket.receive(packet);
			handler.handlePacket(packet);
			
		}
	}

}
