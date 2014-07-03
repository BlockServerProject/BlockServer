package blockserver.net.v081.login;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Random;

import blockserver.MinecraftPEServer;
import blockserver.MinecraftVersion;
import blockserver.net.v081.BaseLoginPacket;
import blockserver.Utils;

public class ConnectedPingPacket extends BaseLoginPacket {
	private DatagramPacket packet;
	private ByteBuffer buffer;
	private MinecraftPEServer server;
	//Fields:
	protected byte PID;
	protected long pingID;
	protected byte[] MAGIC;
	
	
	public ConnectedPingPacket(DatagramPacket packet, MinecraftPEServer server){
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
	
	public ByteBuffer getResponse(String serverName){
		   byte[] magic = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");
		   ByteBuffer bb = ByteBuffer.allocate(35 + serverName.length());
		   Random r = new Random();
		   
		   long pingID = System.currentTimeMillis() - server.startTime;
		   long serverID = r.nextLong();
		   short nameData = (short) serverName.length();
		   byte[] serverType = serverName.getBytes();
		   
		   bb.put((byte)0x1c).putLong(pingID).putLong(serverID).put(magic).putShort(nameData).put(serverType);
		   
		   return bb;
		  }
}
