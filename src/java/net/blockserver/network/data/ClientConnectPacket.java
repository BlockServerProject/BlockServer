package net.blockserver.network.data;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import net.blockserver.Server;

public class
        ClientConnectPacket implements BaseDataPacket{
	private Server server;
	private DatagramPacket packet;
	private ByteBuffer buffer;
	
	public byte PID;
	public long clientID;
	public long session;
	public byte unknown;
	
	public ClientConnectPacket(DatagramPacket packet, Server server){
		this.packet = packet;
		this.server = server;
		
		buffer = ByteBuffer.wrap(packet.getData());
	}
	
	public void decode(){
		buffer.get(); //PID
		buffer.get(new byte[3]); //Count
		byte EID = buffer.get(); //EID
		buffer.get(new byte[3]); //Count
		
		PID = buffer.get(); //PID
		clientID = buffer.getLong();
		session = buffer.getLong();
		unknown = buffer.get();
	}
	
	public void encode() {}

}
