package blockserver.core;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;

import blockserver.networking.packet.*;
import blockserver.utils.logging.*;
import blockserver.utils.*;

public class BlockServerThread extends Thread {
	//Protected Variables
	protected final static String VERSION = "Pre-Alpha 0.1";
	protected DatagramSocket socket;
	protected final static Logger logger = Logger.getLogger(BlockServerThread.class.getName());
	
	//Private variables
	private boolean running = true;
	private long pingID;
	private long serverID;
	private int port;
	private long startTime;
	private Utils utils;
	private ConsoleHelper ch;
	private Thread consoleThread;

	public BlockServerThread(int port) throws SocketException{
		this.port = port;
		utils = new Utils();
		this.serverID = generateServerID();
		
		Logger rootLogger = Logger.getLogger("");
	    Handler[] handlers = rootLogger.getHandlers();
	    if (handlers[0] instanceof ConsoleHandler) {
	      rootLogger.removeHandler(handlers[0]);
	    }
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new BlockLogger());
		logger.addHandler(handler);
		ch = new ConsoleHelper(this);
		consoleThread = new Thread(ch, "ConsoleHelper");
		consoleThread.start();
		
	}
	
	private long generateServerID(){
		long number;
		Random r = new Random();
		number = (long)(r.nextDouble()*1000000);
		return number;
	}
	
	public long getPingID(){
		return pingID;
	}
	
	public long getServerID(){
		return serverID;
	}
	
	protected void stopServer(){
		//TODO: Make sure to disconnect all players, and save the world
		logger.info("Stopping server...");
		running = false;
		logger.info("Server stopped (Was running for: "+(System.currentTimeMillis() - startTime)+" ms)");
		System.exit(0);
	}
	
	public void run(){
		logger.info("Starting BlockServer version: "+VERSION+", implementing MCPE: 0.8.1 Alpha");
		try {
			socket = new DatagramSocket(port);
			startTime = System.currentTimeMillis();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			logger.severe("FAILED TO BIND TO PORT!");
			logger.severe("A detailed message of the error is displayed below: ");
			logger.severe(e.getMessage());
			running = false;
		}
		logger.info("Done! ("+(System.currentTimeMillis() - startTime)+ " ms)");
		while(running){
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				byte[] raw = packet.getData();
				String decoded = new String(raw);
				logger.info("[INFO]: Recived data: "+decoded+" First byte: "+raw[0]);
				handlePacket(packet);
				/*
				String strData = "hello";
				byte[] data = strData.getBytes();
				InetAddress lstAddress = packet.getAddress();
				int lstPort = packet.getPort();
				packet = new DatagramPacket(data, data.length, lstAddress, lstPort);
				socket.send(packet);
				*/
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	protected void handlePacket(DatagramPacket packet){
		String ip = packet.getAddress().getHostAddress();
		int port = packet.getPort();
		Byte packetID = packet.getData()[0];
		String id = packetID.toString();
		byte[] data = packet.getData();
		if(id.equals("1")){
			logger.info("[INFO]: Recived ID_CONNECTED_PING_OPEN_CONNECTIONS, ID: "+packetID);
			OpenConnectionsPacket pk = new OpenConnectionsPacket(packet);
			sendServerInfo(pk, packet);
		}
		else if(id.equals("2")){
			logger.info("[INFO]: Recived ID_UNCONNECTED_PING_OPEN_CONNECTIONS, ID: "+packetID);
			OpenConnectionsPacket pk = new OpenConnectionsPacket(packet);
			sendServerInfo(pk, packet);
		}
		else if(packetID == new Byte("5")){
			//TODO: Raknet Packet
			logger.info("[INFO]: Packet ID_OPEN_CONNECTION_REQUEST_1 is a RakNet packet, unimplemented.");
		}
		else if(packetID == new Byte("7")){
			//TODO: Raknet Packet
			logger.info("[INFO]: Packet ID_OPEN_CONNECTION_REQUEST_2 is a RakNet packet, unimplemented.");
		}
		else {
			logger.info("[ERROR]: Packet "+packetID+" is not implemented in this version.");
			
		}
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	private void sendServerInfo(OpenConnectionsPacket pk, DatagramPacket packet){
		DatagramPacket response = null;
		ServerInfoPacket infoPacket = new ServerInfoPacket(pk, this);
		response = infoPacket.getPacket("MCCPP;Demo;TestServer");
		try {
			socket.send(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[INFO]: Sent data");
	}

}

class ConsoleHelper implements Runnable{
	private BlockServerThread server;
	
	public ConsoleHelper(BlockServerThread server){
		this.server = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String data;
		try {
			boolean looping = true;
			while(looping){
				data = br.readLine();
				//server.logger.info("ConsoleInput: "+data); //Debug
				if(data.equals("stop")){
					looping = false;
					server.stopServer();
				}
				else{
					server.logger.info("Command "+data+" not implemented.");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
