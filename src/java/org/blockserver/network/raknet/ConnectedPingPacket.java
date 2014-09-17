package org.blockserver.network.raknet;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import org.blockserver.Server;

public class ConnectedPingPacket extends BaseLoginPacket {
	private DatagramPacket packet;
	private ByteBuffer buffer;
	private Server server;
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

	@Override
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(packet.getData());
	}
	@Override
	public byte getPID(){
		return PID;
	}
	@Override
	public DatagramPacket getPacket(){
		return packet;
	}
	@Override
	public ByteBuffer getResponse(){
	   String serverName = "MCCPP;Demo;".concat(server.getServerName());
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
