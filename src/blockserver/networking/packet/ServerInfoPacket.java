package blockserver.networking.packet;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import blockserver.core.BlockServerThread;
import blockserver.utils.*;

public class ServerInfoPacket implements BasePacket {
	protected String ip;
	protected int port;
	protected byte packetID;
	protected byte[] buffer;
	protected OpenConnectionsPacket packet;
	
	private Utils utils;
	
	private BlockServerThread server;
	
	public ServerInfoPacket(OpenConnectionsPacket packet, BlockServerThread server){
		/**
		 * This implements a ID_UNCONNECTED_PING_OPEN_CONNECTIONS (0x1C) packet
		 * 
		 */
		ip = packet.getIP();
		port = packet.getPort();
		packetID = new Byte("12");
		this.packet = packet;
		this.server = server;
		utils = new Utils();
	}
	
	public DatagramPacket getPacket(String serverName){
		//TODO: Work on this
		DatagramPacket response = null;
		byte[] magic = Utils.hexStringToByteArray("0x00ffff00fefefefefdfdfdfd12345678");
		Short nameLength = (short) serverName.length();
		buffer = new byte[48 + serverName.length()];
		System.out.println("Buffer: "+buffer.length);
		buffer[0] = new Byte("28"); //Set the packet id
		
		Long pingID = System.currentTimeMillis() - server.getStartTime();
		Long serverID = server.getServerID();
		
		byte[] finalPing = ByteBuffer.allocate(8).putLong(pingID).array();
		byte[] finalServer = ByteBuffer.allocate(8).putLong(serverID).array();
		byte[] finalNameLen = ByteBuffer.allocate(nameLength.SIZE).putShort(nameLength).array();
		byte[] finalName = serverName.getBytes();
		
		int start = 1;
		int index = 1;
		for (int i = 0; i < finalPing.length; i ++) {
		   buffer[start + i] = finalPing[i];
		   index++;
		}
		start = index;
		System.out.println("Starting at: "+index);
		for (int i = 0; i < finalServer.length; i ++) {
		   buffer[start + i] = finalServer[i];
		   index++;
		}
		start = index;
		System.out.println("Starting at: "+index);
		for (int i = 0; i < magic.length; i ++) {
		   buffer[start + i] = magic[i];
		   index++;
		}
		start = index;
		System.out.println("Starting at: "+index);
		for (int i = 0; i < finalNameLen.length; i ++) {
		   buffer[start + i] = finalNameLen[i];
		   index++;
		}
		start = index;
		System.out.println("Starting at: "+index);
		for (int i = 0; i < finalName.length; i ++) {
		   buffer[start + i] = finalName[i];
		   index++;
		}
		
		response = new DatagramPacket(buffer, buffer.length, packet.packet.getAddress(), port);
		System.out.println(Arrays.toString(buffer));
		System.out.println(buffer.length);
		return response;
		
	}

	@Override
	public String getIP() {
		return ip;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public byte getPacketID() {
		return packetID;
	}

	@Override
	public byte[] getBuffer() {
		return buffer;
	}

}
