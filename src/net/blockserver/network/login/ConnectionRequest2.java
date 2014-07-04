package net.blockserver.network.login;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import net.blockserver.Server;
import net.blockserver.network.BaseLoginPacket;
import net.blockserver.utility.Utils;

public class ConnectionRequest2 extends BaseLoginPacket {
	private Server server;
	private DatagramPacket packet;
	
	protected ByteBuffer buffer;
	private byte PID;
	private byte[] MAGIC;
	private byte[] securityCookie;
	private short mtuSize;
	private long clientID;
	
	public ConnectionRequest2(DatagramPacket packet, Server server){
		this.server = server;
		this.packet = packet;
		
		buffer = ByteBuffer.wrap(packet.getData());
		PID = buffer.get();
		MAGIC = buffer.get(new byte[16]).array();
		securityCookie = buffer.get(new byte[4]).array();
		mtuSize = buffer.getShort();
		clientID = buffer.getLong();
	}
	
	public ByteBuffer getResponse(){
		ByteBuffer response = ByteBuffer.allocate(30);
		byte packetID = (byte) 0x08;
		byte[] magic = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");
		long serverID = server.serverID;
		short clientPort = (short) packet.getPort();
		short mtu = mtuSize;
		byte security = 0;
		
		response.put(packetID);
		response.put(magic);
		response.putLong(serverID);
		response.putShort(mtu);
		response.putShort(mtu);
		response.put(security);
		
		return response;
		
	}
	
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(packet.getData());
	}
	
	public byte getPID(){
		return PID;
	}
	
	public DatagramPacket getPacket(){
		return packet;
	}

}
