package net.blockserver.network.minecraft;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

import net.blockserver.Player;
import net.blockserver.network.raknet.BaseLoginPacket;

/**
 * Implements a 0x03 PONG Packet.
 */
public class PongPacket extends BaseLoginPacket {
	
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
	
	public byte getPID(){
		return this.PID;
	}
	
	public ByteBuffer getResponse(){
		return null;
	}
	
	public DatagramPacket getPacket(){
		DatagramPacket response = null;
		buffer = ByteBuffer.allocate(17);
		
		buffer.put(this.PID);
		buffer.putLong(this.pingID);
		buffer.putLong(this.unknown);
		
		InetAddress addr;
		try {
			addr = client.getAddress();
			response = new DatagramPacket(buffer.array(), buffer.capacity());
			response.setAddress(addr);
			response.setPort(client.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	public ByteBuffer getBuffer(){
		return this.buffer;
	}

}
