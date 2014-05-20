package blockserver.networking.packet;

import java.net.*;


public class OpenConnectionRequest implements BasePacket{
	protected String ip;
	protected int port;
	protected byte packetID;
	protected byte[] buffer;
	protected DatagramPacket packet;
	protected int nullLength;
	protected int protocol;

	public OpenConnectionRequest(DatagramPacket packet){
		ip = packet.getAddress().getHostAddress();
		port = packet.getPort();
		buffer = packet.getData();
		packetID = buffer[0];
		this.packet = packet;
		nullLength = buffer.length-18; //Not sure if that is correct
		protocol = buffer[19];
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
	public int getNullLength(){
	  return nullLength;
	}
	public int getProtocolID(){
	 return protocol;
	}
}
