package net.blockserver;

import net.blockserver.network.BasePacketHandler;
import net.blockserver.network.PacketHandler081;
import net.blockserver.scheduler.Scheduler;
import net.blockserver.utility.MinecraftVersion;
import net.blockserver.utility.ServerLogger;

import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

public class Server {
	private ServerLogger serverLog = ServerLogger.getInstance();
	private MinecraftVersion MCVERSION;
	private Scheduler scheduler;
	private String VERSION = "0.05";
	private boolean serverRunning;
	private long startTime;
	private long serverID;
	
	protected int serverPort;
	protected String serverName;
	protected int maxPlayers;
	protected int mtu = 512;
	
	private DatagramSocket networkSocket;

    public MinecraftVersion getMinecraftVersion() {
        return this.MCVERSION;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getVersion() {
        return this.VERSION;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public int getMaxPlayers() { return this.maxPlayers; }

    public long getServerID() {
        return this.serverID;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean isRunning() {
        return this.serverRunning;
    }

	public Server(int port, MinecraftVersion version, String name, int players){
        Thread.currentThread().setName("Server");
		this.startTime = System.currentTimeMillis();
		this.serverPort = port;
		this.serverName = "MCCPP;MINECON;"+name;
		this.maxPlayers= players;
		this.MCVERSION = version;
		this.serverID = new Random().nextLong();
		this.scheduler = new Scheduler();// Minecraft Deafult Ticks Per Seconds(20)
	}
	
	public void updateMTU(int mtu){
		this.mtu = mtu;
	}
	
	public void run(){
		try {
			serverLog.log(Level.INFO, "Starting server on: *:" + serverPort + ", implementing " + MinecraftVersion.versionToString(MCVERSION));
			serverLog.info("This is version "+VERSION);
			networkSocket = new DatagramSocket(serverPort);
			serverRunning = true;
			scheduler.Start();
			serverLog.info("Server Scheduler Started...");
			handlePackets();
		} catch (SocketException e) {
			serverLog.fatal("COULD NOT BIND TO PORT - Maybe another server is running on "+serverPort+"?");
			serverLog.fatal("Shutting down server due to error.");
			System.exit(1);
		} catch (IOException e) {
			int time = (int) (System.currentTimeMillis() - startTime);
			serverLog.warning("IOException at: " + time + " ms");
		} catch (Exception e) {
			int time = (int) (System.currentTimeMillis() - startTime);
			serverLog.warning("Exception at: " + time + " ms");
			serverLog.warning(e.getMessage());
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

	public void Stop() throws Exception{
		this.serverRunning = false;
		this.scheduler.Stop();
	}

}
