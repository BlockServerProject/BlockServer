package net.blockserver.network.minecraft;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Represents a 0x00 PING Packet.
 */

public class PingPacket implements BaseDataPacket {
	private DatagramPacket pkt;
	
	public byte PID;
	public long pingID;
	public byte unknown; //Not sure about this, length is 10 bytes, 9 bytes for PID and PingID.
	
	public PingPacket(DatagramPacket p){
		this.pkt = p;
	}
	
	public PingPacket(byte[] b){
		this.pkt = new DatagramPacket(b, b.length);
	}
	
	public void decode(){
		ByteBuffer bb = ByteBuffer.wrap(pkt.getData());
		PID = bb.get();
		pingID = bb.getLong();
		unknown = bb.get();
		
	}
	
	public void encode(){};
	public ByteBuffer getBuffer(){
		return ByteBuffer.wrap(pkt.getData());
	}

}
