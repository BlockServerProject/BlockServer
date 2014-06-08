package blockserver.core;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.*;

import blockserver.networking.packets.login.CS01Packet;
import blockserver.networking.packets.login.CS05Packet;
import blockserver.networking.packets.login.CS07Packet;
import blockserver.networking.packets.login.IncompatibleProtocolPacket;
import blockserver.networking.packets.login.SC01cPacket;
import blockserver.networking.packets.login.SC06Packet;
import blockserver.networking.packets.login.SC08Packet;
import blockserver.utils.logging.*;
import blockserver.utils.*;

public class BlockServerThread implements Runnable {
	//Protected Variables
	protected final static String VERSION = "v0.0.25-PreAlpha";
	protected final static String IMPLEMENT = "MCPE v0.8.1-Alpha";
	protected final static int RAKNETPROTOCOL = 5;
	protected DatagramSocket socket;
	protected HashMap<InetAddress, Player> players;
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
		players = new HashMap<InetAddress, Player>();
		generateServerID();
		
		Logger rootLogger = Logger.getLogger("");
	    Handler[] handlers = rootLogger.getHandlers();
	    if (handlers[0] instanceof ConsoleHandler) {
	      rootLogger.removeHandler(handlers[0]);
	    }
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new BlockLogger());
		logger.addHandler(handler);
		startTime = System.currentTimeMillis();
		logger.info("Starting BlockServer version: "+VERSION+", implementing "+IMPLEMENT);
		ch = new ConsoleHelper(this);
		consoleThread = new Thread(ch, "ConsoleHelper");
		consoleThread.start();
		logger.info("Started console handler...");
		
	}
	
	public Logger getLogger(){
		return logger;
	}
	
	private void generateServerID(){
		Random r = new Random();
		serverID = r.nextLong();
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
		logger.info("Server stopped.");
		System.exit(0);
	}
	
	public void run(){
		//Display info
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			logger.severe("FAILED TO BIND TO PORT!");
			logger.severe("A detailed message of the error is displayed below: ");
			logger.severe(e.getMessage());
			running = false;
			System.exit(1);
		}
		logger.info("Done! ("+(System.currentTimeMillis() - startTime)+ " ms)");
		while(running){
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				byte[] raw = packet.getData();
				String decoded = new String(raw);
				handlePacket(packet);
				/*
				String strData = "hello";
				byte[] data = strData.getBytes();
				InetAddress lstAddress = packet.getAddress();
				int lstPort = packet.getPort();
				packet = new DatagramPacket(data, data.length, lstAddress, lstPort);
				socket.send(packet);
				*/
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.severe("Shutting down server due to error.");
				running = false;
				System.exit(1);
			}
			
		}
	}
	
	@SuppressWarnings("finally")
	protected void handlePacket(DatagramPacket packet) throws UnknownHostException{
		String ip = packet.getAddress().getHostAddress();
		int port = packet.getPort();
		Byte packetID = packet.getData()[0];
		String id = packetID.toString();
		byte[] data = packet.getData();
		CS01Packet pk = null;
		CS07Packet pkt = null;
		long CID = 0;
		
		switch(packetID){
		case 0x01:
			logger.fine("Recived ID_CONNECTED_PING_OPEN_CONNECTIONS, ID: "+packetID);
			pk = new CS01Packet(packet);
			sendServerInfo(pk, packet);
			break;
		case 0x02:
			logger.fine("Recived ID_UNCONNECTED_PING_OPEN_CONNECTIONS, ID: "+packetID);
			pk = new CS01Packet(packet);
			sendServerInfo(pk, packet);
			break;
		case 0x05:
			CS05Packet ocr = new CS05Packet(packet);
			logger.info("Recived packet 0x05, protocol is: "+ocr.getProtocolID());
			if(ocr.getProtocolID() != RAKNETPROTOCOL){
				//TODO: Send a 0x1A packet
				IncompatibleProtocolPacket ipp = new IncompatibleProtocolPacket(ocr, RAKNETPROTOCOL, serverID);
				try {
					socket.send(ipp.getPacket());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.severe("FAILED TO SEND A PACKET");
					logger.severe("A detailed message is displayed below:");
					logger.severe(e.getMessage());
				}
				break;
			}
			SC06Packet ocp = new SC06Packet(ocr, this);
			DatagramPacket reply = ocp.getPacket();
			try {
				socket.send(reply);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.severe("FAILED TO SEND A PACKET");
				logger.severe("A detailed message is displayed below:");
				logger.severe(e.getMessage());
			}
			finally{
				break;
			}
		case 0x07:
			pkt = new CS07Packet(packet, this);
			CID = pkt.getClientID();
			logger.info("Recived packet 0x07, sending 0x08...");
			SC08Packet pkt8 = new SC08Packet(pkt, this);
			DatagramPacket response = pkt8.getPacket();
			try {
				socket.send(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.severe("FAILED TO SEND A PACKET");
				logger.severe("A detailed message is displayed below:");
				logger.severe(e.getMessage());
			}
			
			break;
			
		case (byte) 0x84:
			//DATA 0x09
			if(! players.containsKey(packet.getAddress())){
				Player thePlayer = new Player(packet.getAddress().getHostAddress(), packet.getPort(), 3, CID);
				players.put(packet.getAddress(), thePlayer);
				logger.info("New player connected from: "+packet.getAddress().getHostAddress()+":"+packet.getPort());
			}
			
		default:
			Player player = null;
			
			if(players.containsKey(packet.getAddress())){
				player = players.get(packet.getAddress());
				DataHandler dh = new DataHandler(packet, this, player);
				if(dh.handleData()){
					//Success
					logger.info("Success! Data packet: "+packetID+" handled!");
				}
				else{
					logger.warning("Recived packet: "+packetID+", and it is not implemented!");
				}
			}
			
		}
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	private void sendServerInfo(CS01Packet pk, DatagramPacket packet){
		DatagramPacket response = null;
		SC01cPacket infoPacket = new SC01cPacket(pk, this);
		//TODO: Get this from config
		response = infoPacket.getPacket("MCCPP;MINECON;BlockServer "+VERSION+" Test");
		try {
			socket.send(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.fine("Sent server info.");
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
