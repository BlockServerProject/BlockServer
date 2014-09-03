package org.blockserver.network.raknet;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import org.blockserver.Server;

public class ConnectionRequest1Packet extends BaseLoginPacket {
	private DatagramPacket packet;
	private Server server;
	protected ByteBuffer buffer;
	public byte PID;
	public byte[] MAGIC;
	public byte protocolVersion;
	public byte[] nullPayload;

	public ConnectionRequest1Packet(DatagramPacket packet, Server server){
		this.packet = packet;
		this.server = server;
		buffer = ByteBuffer.wrap(packet.getData());
		PID = buffer.get();
		MAGIC = buffer.get(new byte[16]).array();
		protocolVersion = buffer.get();
		nullPayload = buffer.get(new byte[packet.getData().length - 18]).array();
	}

	@Override
	public ByteBuffer getResponse(){
		ByteBuffer response = ByteBuffer.allocate(28);
		byte packetID = (byte) 0x06;
		byte[] magic = this.getMAGIC();
		long serverID = server.getServerID();
		byte security = 0;
		short mtu = (short) nullPayload.length;
		response.put(packetID);
		response.put(magic);
		response.putLong(serverID);
		response.put(security);
		response.putShort(mtu);
		return response;
	}
	public byte getProtocol(){
		return protocolVersion;
	}
	@Override
	public DatagramPacket getPacket(){
		return packet;
	}
	@Override
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(packet.getData());
	}
	@Override
	public byte getPID(){
		return PID;
	}
}
