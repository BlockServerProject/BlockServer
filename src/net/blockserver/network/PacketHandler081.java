package net.blockserver.network;

import net.blockserver.Server;
import net.blockserver.network.login.ConnectedPingPacket;
import net.blockserver.network.login.ConnectionRequest1Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class PacketHandler081 extends BasePacketHandler{
	private DatagramSocket socket;
	private Server server;
	
	public PacketHandler081(DatagramSocket socket, Server server){
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
			ByteBuffer response = packet.getResponse();
			
			DatagramPacket packet1c = new DatagramPacket(response.array(), response.capacity(), udpPacket.getAddress(), udpPacket.getPort());
			sendResponse(packet1c);
			break;
			
		
		case 0x05:
			//ID_OPEN_CONNECTION_REQUEST_1 (0x05)
			//Client to Server
			packet = new ConnectionRequest1Packet(udpPacket, server);
			
			ByteBuffer response6 = packet.getResponse();
			
			DatagramPacket packet06 = new DatagramPacket(response6.array(), response6.capacity(), udpPacket.getAddress(), udpPacket.getPort());
			sendResponse(packet06);
			
			server.serverLog.info("Recived packet 0x05, sent 0x06");
			
			break;
		/*
		case 0x07:
			//ID_OPEN_CONNECTION_REQUEST_2 (0x07)
			//Client to Server
			break;
		*/
		default:
			server.serverLog.warning("RECIVED UNSUPPORTED PACKET: "+PID);
		}
	}

}
