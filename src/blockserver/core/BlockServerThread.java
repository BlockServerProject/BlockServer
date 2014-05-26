package blockserver.core;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.*;

import blockserver.networking.packet.login.client.CS05Packet;
import blockserver.networking.packet.login.client.CS01Packet;
import blockserver.networking.packet.login.server.SC01dPacket;
import blockserver.networking.packet.login.server.SC06Packet;
import blockserver.utils.logging.*;
import blockserver.utils.*;

public class BlockServerThread implements Runnable {
	//Protected Variables
	protected final static String VERSION = "Pre-Alpha 0.0.1-DEV";
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
		generateServerID();
		
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
		switch(packetID){
		case 0x01:
			logger.fine("Recived ID_CONNECTED_PING_OPEN_CONNECTIONS, ID: "+packetID);
			pk = new CS01Packet(packet);
			break;
		case 0x02:
			logger.fine("Recived ID_UNCONNECTED_PING_OPEN_CONNECTIONS, ID: "+packetID);
			pk = new CS01Packet(packet);
			sendServerInfo(pk, packet);
			break;
		case 0x05:
			CS05Packet ocr = new CS05Packet(packet);
			logger.info("Recived packet 0x05, protocol is: "+ocr.getProtocolID());
			System.out.println(Arrays.toString(ocr.getBuffer()));
			//TODO: Figure out the protocol number, and test it
			SC06Packet ocp = new SC06Packet(ocr, this);
			DatagramPacket reply = ocp.getPacket();
			try {
				socket.send(reply);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				break;
			}
		case 0x07:
			//TODO: Implement packet 0x07
			logger.info("Packet 0x07 is not implemented in this version.");
			break;
			
		default:
			logger.warning("Recived packet: "+packetID+", and it is not implemented!");
		}
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	private void sendServerInfo(CS01Packet pk, DatagramPacket packet){
		DatagramPacket response = null;
		SC01dPacket infoPacket = new SC01dPacket(pk, this);
		response = infoPacket.getPacket("MCCPP;MINECON;TEST");
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
