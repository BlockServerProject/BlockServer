package net.blockserver.network.data;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import net.blockserver.Server;
import net.blockserver.network.BaseDataPacket;

public class CustomPacket extends BaseDataPacket {
	private DatagramPacket packet;
	private Server server;
	
	protected ByteBuffer buffer;
	public long clientID;
	public long session;
	public byte unknown;

	public CustomPacket(DatagramPacket packet, Server server){
		this.packet = packet;
		this.server = server;
		
		buffer = ByteBuffer.wrap(packet.getData());
	}
	
	
	public void decode() {
		buffer.get();
		
	}
	
	public void encode() {}

}
