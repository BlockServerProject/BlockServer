package blockserver.net.v081;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

import blockserver.MinecraftPEServer;
import blockserver.net.BasePacketHandler;
import blockserver.net.v081.login.ConnectedPingPacket;

public class PacketHandler081 extends BasePacketHandler{
	private DatagramSocket socket;
	private MinecraftPEServer server;
	
	public PacketHandler081(DatagramSocket socket, MinecraftPEServer server){
		this.socket = socket;
		this.server = server;
	}
	
	public void sendResponse(DatagramPacket response){
		try {
			socket.send(response);
		} catch (IOException e) {
			server.serverLog.warning("Failed to send packet - PacketHandler");
		}
	}
	
	public void handlePacket(DatagramPacket udpPacket){
		byte PID = udpPacket.getData()[0];
		BasePacket packet;
		switch(PID){
		case 0x01:
			//ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
			//Client to Broadcast
			packet = new ConnectedPingPacket(udpPacket, server);
			ByteBuffer response = packet.getResponse("MCCPP;MINECON;Test");
			
			DatagramPacket rspPacket = new DatagramPacket(response.array(), response.capacity(), udpPacket.getAddress(), udpPacket.getPort());
			sendResponse(rspPacket);
			
			
		case 0x05:
			//ID_OPEN_CONNECTION_REQUEST_1 (0x05)
			//Client to Server
		case 0x07:
			//ID_OPEN_CONNECTION_REQUEST_2 (0x07)
			//Client to Server
		}
	}

}
