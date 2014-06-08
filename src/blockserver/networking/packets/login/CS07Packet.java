package blockserver.networking.packets.login;

import java.net.*;
import java.nio.ByteBuffer;

import blockserver.core.BlockServerThread;
import blockserver.networking.packet.base.BasePacket;
import blockserver.utils.Utils;

public class CS07Packet implements BasePacket {
	public byte packetID;
	
	private BlockServerThread server;
	private static final byte[] MAGIC = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");
	private DatagramPacket clientPacket;
	
	//Packet data
	private byte security;
	private byte[] cookie;
	private short serverUDPPort;
	private short mtuSize;
	private long clientID;
	
	protected InetAddress ip;
	protected int port;
	protected ByteBuffer buffer;

	public CS07Packet(DatagramPacket packet, BlockServerThread server) {
		// TODO Auto-generated constructor stub
		clientPacket = packet;
		this.server = server;
		ip = packet.getAddress();
		port = packet.getPort();
		
		//Assign packet values
		buffer = ByteBuffer.wrap(packet.getData());
		packetID = buffer.get();
		security = buffer.get();
		cookie = buffer.get(new byte[3]).array();
		serverUDPPort = buffer.getShort();
		mtuSize = buffer.getShort();
		clientID = buffer.getLong();
	}
	
	public byte getSecurity(){
		return security;
	}
	
	public byte[] getCookie(){
		return cookie;
	}
	public short getServerUDPPort(){
		return serverUDPPort;	
	}
	public short getMtuSize(){
		return mtuSize;
	}
	public long getClientID(){
		return clientID;
	}

	public DatagramPacket getRawPacket(){
		return clientPacket;
	}
	
	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return ip.getHostAddress();
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return port;
	}

	@Override
	public byte getPacketID() {
		// TODO Auto-generated method stub
		return packetID;
	}

	@Override
	public byte[] getBuffer() {
		// TODO Auto-generated method stub
		return buffer.array();
	}

}
