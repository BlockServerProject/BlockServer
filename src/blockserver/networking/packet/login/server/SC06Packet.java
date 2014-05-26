package blockserver.networking.packet.login.server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import blockserver.core.BlockServerThread;
import blockserver.networking.packet.base.BasePacket;
import blockserver.networking.packet.login.client.CS05Packet;
import blockserver.utils.*;

public class SC06Packet implements BasePacket {
	protected String ip;
	protected int port;
	protected ByteBuffer buffer;
    protected byte packetID;
	protected Short mtu;
	
	private Utils utils;
	private CS05Packet packet;
	private byte[] magic = new byte[16];

	private BlockServerThread server;

	public SC06Packet(CS05Packet packet, BlockServerThread server){
		/**
		 * Implements a 0x06 packet
		 */
		ip = packet.getIP();
		port = packet.getPort();
		this.server = server;
		utils = new Utils();
		mtu = (short) packet.getMtuSize();
        packetID = (byte) 0x06;
        buffer = ByteBuffer.allocate(28);
	}

	public DatagramPacket getPacket() throws UnknownHostException{
		
		DatagramPacket response = null;
		
		buffer.put(packetID);
		buffer.put(magic);
		buffer.putLong(server.getServerID());
		buffer.put((byte) 0x00);
		buffer.putShort(mtu);
		
		System.out.println(Arrays.toString(buffer.array()));
		InetAddress address = InetAddress.getByName(ip);

		response = new DatagramPacket(buffer.array(), buffer.capacity(), address, port);
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
		return buffer.array();
	}

}