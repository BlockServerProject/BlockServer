package net.blockserver.network.login;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import net.blockserver.Server;
import net.blockserver.network.BaseLoginPacket;
import net.blockserver.utility.Utils;

public class IncompatibleProtocolPacket{
	private InetAddress address;
	private int port;
	private byte correctProtocol;
	private Server server;
	
	public IncompatibleProtocolPacket(InetAddress address, int port, byte correctProtocol, Server server){
		this.address = address;
		this.port = port;
		this.correctProtocol = correctProtocol;
		this.server = server;
	}
	
	public DatagramPacket getPacket(){
		DatagramPacket response = null;
		ByteBuffer bb = ByteBuffer.allocate(26);
		bb.put((byte) 0x1A); //PacketID
		bb.put(correctProtocol);
		bb.put(Utils.hexStringToByteArray("00ffff00fefefefefdfdfdfd12345678"));
		bb.putLong(server.getServerID());
		
		response = new DatagramPacket(bb.array(), bb.capacity(), address, port);
		
		return response;
	}

}
