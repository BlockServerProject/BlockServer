package blockserver.networking.packet.login.client;

import java.net.*;
import java.nio.ByteBuffer;

import blockserver.networking.packet.base.BasePacket;



public class CS01Packet implements BasePacket{
	/**
	 * Implements a ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01) Packet.
	 * Also works for a ID_UNCONNECTED_PING_OPEN_CONNECTIONS (0x02) Packet.
	 */
	public String ip;
	public int port;
	public byte packetID;
	public static byte[] MAGIC;
	public long clientID;
	public byte[] buffer;
	public DatagramPacket packet;
	
	public CS01Packet(DatagramPacket packet){
		ByteBuffer bb = ByteBuffer.wrap(packet.getData());
		ip = packet.getAddress().getHostAddress();
		port = packet.getPort();
		buffer = packet.getData();
		packetID = bb.get();
		clientID = bb.getLong();
		MAGIC = bb.get(new byte[16]).array();
		this.packet = packet;
	}
	
	public String getIP(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}
	
	public byte getPacketID(){
		return packetID;
	}
	
	public byte[] getBuffer(){
		return buffer;
	}
	

}
