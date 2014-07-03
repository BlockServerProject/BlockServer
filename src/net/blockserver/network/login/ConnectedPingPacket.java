package net.blockserver.network.login;

import net.blockserver.Server;
import net.blockserver.network.BaseLoginPacket;
import net.blockserver.utility.Utils;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Random;

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
	   byte[] magic = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");
	   ByteBuffer bb = ByteBuffer.allocate(35 + serverName.length());
	   
	   long pingID = System.currentTimeMillis() - server.startTime;
	   long serverID = server.serverID;
	   short nameData = (short) serverName.length();
	   byte[] serverType = serverName.getBytes();
	   
	   bb.put((byte)0x1c).putLong(pingID).putLong(serverID).put(magic).putShort(nameData).put(serverType);
	   
	   return bb;
	}
}