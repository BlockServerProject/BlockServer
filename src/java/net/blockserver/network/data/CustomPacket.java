package net.blockserver.network.data;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import net.blockserver.Server;
import net.blockserver.network.BaseDataPacket;

public class CustomPacket extends BaseDataPacket {
	private DatagramPacket packet;
	private Server server;
	
	protected ByteBuffer buffer;

	public CustomPacket(DatagramPacket packet, Server server){
		this.packet = packet;
		this.server = server;
		
		buffer = ByteBuffer.wrap(packet.getData());
	}
	
	
	public void decode() {
		
		
	}
	public void encode() {}
	public ByteBuffer getBuffer() {return null;}
	public byte getPID() {return (Byte) null;}
	public DatagramPacket getPacket() {return null;}
	public ByteBuffer getResponse() { return null; }

}
