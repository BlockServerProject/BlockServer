package net.blockserver.network.login;

import net.blockserver.Server;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class ConnectedPingPacket extends BaseLoginPacket {
	private DatagramPacket packet;
	private ByteBuffer buffer;
	private Server server;
	//Fields:
	protected byte PID;
	protected long pingID;
	protected byte[] MAGIC;
	
	
	public ConnectedPingPacket(DatagramPacket packet, Server server){
		this.packet = packet;
		this.server = server;
		buffer = ByteBuffer.wrap(packet.getData());
		PID = buffer.get();
		pingID = buffer.getLong();
		MAGIC = buffer.get(new byte[16]).array();
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
	
	public ByteBuffer getResponse(){
	   String serverName = server.getServerName();
	   byte[] magic = this.getMAGIC();
	   ByteBuffer bb = ByteBuffer.allocate(35 + serverName.length());
	   
	   long pingID = System.currentTimeMillis() - server.getStartTime();
	   long serverID = server.getServerID();
	   short nameData = (short) serverName.length();
	   byte[] serverType = serverName.getBytes();
	   
	   bb.put((byte)0x1c).putLong(pingID).putLong(serverID).put(magic).putShort(nameData).put(serverType);
	   
	   return bb;
	}
}