package net.blockserver.network.minecraft;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

import net.blockserver.Player;

/**
 * Implements a 0x03 PONG Packet.
 */
public class PongPacket implements BaseDataPacket {
	
	private Player client;
	private ByteBuffer buffer;
	
	public final byte PID = PacketsID.PONG;
	public final long pingID;
	public final long unknown;
	
	public PongPacket(Player client, long pingID){
		this.client = client;
		this.pingID = pingID;
		Random r = new Random();
		unknown = r.nextLong();
	}
	
	public void encode(){
		buffer = ByteBuffer.allocate(17);
		
		buffer.put(this.PID);
		buffer.putLong(this.pingID);
		buffer.putLong(this.unknown);
	}
	
	public void decode(){};
	public ByteBuffer getBuffer(){
		return this.buffer;
	}

}
