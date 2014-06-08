package blockserver.networking.packets.login;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import blockserver.networking.packet.base.BasePacket;
import blockserver.utils.*;

public class IncompatibleProtocolPacket implements BasePacket {
	private InetAddress ip;
	private int port;
	private byte packetID;
	
	protected ByteBuffer buffer;
	protected int protocol;
	protected long serverID;
	protected static byte[] MAGIC = Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678");

	public IncompatibleProtocolPacket(CS05Packet pkt, int protocol, long serverID) throws UnknownHostException {
		this.protocol = protocol;
		this.serverID = serverID;
		this.buffer = ByteBuffer.allocate(26);
		this.ip = InetAddress.getByName(pkt.getIP());
		this.port = pkt.getPort();
	}
	
	public DatagramPacket getPacket(){
		DatagramPacket response = null;
		
		buffer.put(packetID);
		buffer.put(MAGIC);
		buffer.putLong(serverID);
		
		response = new DatagramPacket(buffer.array(), buffer.capacity(), ip, port);
		return response;
	}

	public String getIP() {
		return ip.getHostName();
	}

	public int getPort() {
		return port;
	}

	public byte getPacketID() {
		return packetID;
	}

	public byte[] getBuffer() {
		return buffer.array();
	}

}
