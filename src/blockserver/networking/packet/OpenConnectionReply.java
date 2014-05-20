package blockserver.networking.packet;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import blockserver.core.BlockServerThread;
import blockserver.utils.*;

public class OpenConnectionReply implements BasePacket {
	protected String ip;
	protected int port;
	protected byte[] buffer;
	private Utils utils;
	protected int mtuSize;
	
	private BlockServerThread server;
	
	public OpenConnectionReply(OpenConnectionRequest packet, BlockServerThread server){
		ip = packet.getIP();
		port = packet.getPort();
		this.server = server;
		utils = new Utils();
		mtu = packet.getNullLength();
	}
	
	public DatagramPacket getPacket(){
		DatagramPacket response = null;
		buffer = new byte[28];
		buffer[0] = new Byte("6");
		
		Long serverID = server.getServerID();
		
		byte[] magic = Utils.hexStringToByteArray("0x00ffff00fefefefefdfdfdfd12345678");
		byte[] finalServer = ByteBuffer.allocate(8).putLong(serverID).array();
		byte security = new Byte("0");
		byte[] mtuSize = ByteBuffer.allocate(2).putShort(mtu).array();
		
		// Needs to be transferred to buffer
		
		response = new DatagramPacket(buffer, buffer.length, packet.packet.getAddress(), port);
		System.out.println(Arrays.toString(buffer));
		System.out.println(buffer.length);
		return response;
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
