package blockserver.networking.packets.login;

import java.net.*;
import java.nio.ByteBuffer;

import blockserver.networking.packet.base.BasePacket;
import blockserver.utils.Utils;


public class CS05Packet implements BasePacket{
	protected String ip;
	protected int port;
	protected byte packetID;
	protected ByteBuffer buffer;
	public DatagramPacket packet;
	protected short mtu;
	protected int protocol;
	
	private String magic;

	public CS05Packet(DatagramPacket packet){
		ip = packet.getAddress().getHostAddress();
		port = packet.getPort();
		this.packet = packet;
		
		buffer = ByteBuffer.wrap(packet.getData());
		packetID = buffer.get();
		buffer.get(new byte[16]);
		protocol = buffer.get();
		mtu = (short) ((short) packet.getLength() - 18);
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
		return buffer.array();
	}
	
	public int getProtocolID(){
	 return protocol;
	}

	public short getMtuSize() {
		// TODO Auto-generated method stub
		return mtu;
	}
}
