package blockserver.networking.packet.login.server;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

import blockserver.core.BlockServerThread;
import blockserver.networking.packet.base.BasePacket;
import blockserver.networking.packet.login.client.CS01Packet;
import blockserver.utils.Utils;

public class SC01cPacket implements BasePacket {
	protected String ip;
	protected int port;
	protected byte packetID;
	protected byte[] buffer;
	protected CS01Packet packet;
	
	private Utils utils;
	
	private BlockServerThread server;
	
	public SC01cPacket(CS01Packet opk, BlockServerThread server){
		this.server = server;
		this.ip = opk.getIP();
		this.port = opk.getPort();
		this.packetID = new Byte((byte) 0x1c);
		this.packet = opk;
	}
	
	public DatagramPacket getPacket(String serverName){
		   DatagramPacket response = null;
		   byte[] magic = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");
		   //System.out.println(magic.length);
		   ByteBuffer bb = ByteBuffer.allocate(35 + serverName.length());
		   Random r = new Random();
		   
		   long pingID = System.currentTimeMillis() - server.getStartTime();
		   long serverID = r.nextLong();
		   short nameData = (short) serverName.length();
		   byte[] serverType = serverName.getBytes();
		   bb.put(this.packetID).putLong(pingID).putLong(serverID).put(magic).putShort(nameData).put(serverType);
		   //System.out.println(bytArrayToHex(bb.array()));
		   
		   response = new DatagramPacket(bb.array(), bb.capacity(), packet.packet.getAddress(), port);
		   return response;
		  }
	
	private String bytArrayToHex(byte[] a) {
		 StringBuilder sb = new StringBuilder();
		   for(byte b: a){
			   sb.append(String.format("%02x", b&0xff));
		   }
		   return sb.toString();
  }

	@Override
	public String getIP() {
		return ip;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public byte getPacketID() {
		return packetID;
	}

	@Override
	public byte[] getBuffer() {
		return buffer;
	}

}

