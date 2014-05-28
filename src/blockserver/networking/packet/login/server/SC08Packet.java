package blockserver.networking.packet.login.server;

import java.nio.ByteBuffer;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import blockserver.core.*;
import blockserver.utils.*;
import blockserver.networking.packet.base.BasePacket;
import blockserver.networking.packet.login.client.CS07Packet;

public class SC08Packet implements BasePacket {
	private BlockServerThread server;
	private CS07Packet packet07;
	private InetAddress address;
	
	protected String ip;
	protected int port;
	protected byte packetID;
	protected ByteBuffer buffer;

	public SC08Packet(CS07Packet pkt, BlockServerThread server) throws UnknownHostException {
		this.ip = pkt.getIP();
		this.port = pkt.getPort();
		this.packetID = (byte) 0x08;
		this.buffer = ByteBuffer.allocate(30);
		this.server = server;
		address = InetAddress.getByName(ip);
		packet07 = pkt;
	}
	
	public DatagramPacket getPacket(){
		//Fields
		byte[] magic = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");
		long serverID = server.getServerID();
		Integer clientPort = packet07.getRawPacket().getPort();
		short mtuSize = packet07.getMtuSize();
		byte security = 0;
		
		buffer.put(packetID);
		buffer.put(magic);
		buffer.putLong(serverID);
		buffer.putShort(clientPort.shortValue());
		buffer.putShort(mtuSize);
		buffer.put(security);
		
		DatagramPacket response = new DatagramPacket(buffer.array(), buffer.capacity());
		response.setAddress(address);
		response.setPort(clientPort);
		
		return response;
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return ip;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return port;
	}

	@Override
	public byte getPacketID() {
		// TODO Auto-generated method stub
		return packetID;
	}

	@Override
	public byte[] getBuffer() {
		// TODO Auto-generated method stub
		return buffer.array();
	}

}
