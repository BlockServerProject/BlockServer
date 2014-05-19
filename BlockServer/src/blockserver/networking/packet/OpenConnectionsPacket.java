package blockserver.networking.packet;

import java.net.*;



public class OpenConnectionsPacket implements BasePacket{
	protected String ip;
	protected int port;
	protected byte packetID;
	protected byte[] buffer;
	protected DatagramPacket packet;
	
	public OpenConnectionsPacket(DatagramPacket packet){
		ip = packet.getAddress().getHostAddress();
		port = packet.getPort();
		buffer = packet.getData();
		packetID = buffer[0];
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
