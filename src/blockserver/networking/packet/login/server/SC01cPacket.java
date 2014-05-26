package blockserver.networking.packet.login.server;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import blockserver.core.BlockServerThread;
import blockserver.networking.packet.base.BasePacket;
import blockserver.networking.packet.login.client.CS05Packet;
import blockserver.utils.*;

public class SC01cPacket implements BasePacket {
	protected String ip;
	protected int port;
	protected byte packetID;
	protected byte[] buffer;
	protected CS05Packet packet;
	
	private Utils utils;
	
	private BlockServerThread server;
	
	public SC01cPacket(CS05Packet packet, BlockServerThread server){
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
		byte[] finalNameLen = ByteBuffer.allocate(nameLength.SIZE).putShort(nameLength).array();
		buffer = new byte[46 + serverName.length() + finalNameLen.length];
		System.out.println("Buffer: "+buffer.length);
		buffer[0] = new Byte("28"); //Set the packet id
		
		Long pingID = System.currentTimeMillis() - server.getStartTime();
		Long serverID = server.getServerID();
		
		byte[] finalPing = Utils.hexStringToByteArray("0x00000000003c6d0d");
		byte[] finalServer = Utils.hexStringToByteArray("0x00000000372cdc9e");
		byte[] finalName = serverName.getBytes();
		
		int start = 1;
		int index = 0;
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
