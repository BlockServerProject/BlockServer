package net.blockserver.network.login;

import net.blockserver.Server;
import net.blockserver.network.BaseLoginPacket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class IncompatibleProtocolPacket extends BaseLoginPacket{
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

    @Override
    public ByteBuffer getBuffer() {
        return null;
    }

    @Override
    public byte getPID() {
        return (byte) 0x1A;
    }

    public DatagramPacket getPacket(){
		DatagramPacket response = null;
		ByteBuffer bb = ByteBuffer.allocate(26);
		bb.put((byte) 0x1A); //PacketID
		bb.put(correctProtocol);
		bb.put(this.getMAGIC());
		bb.putLong(server.getServerID());
		
		response = new DatagramPacket(bb.array(), bb.capacity(), address, port);
		
		return response;
	}

    @Override
    public ByteBuffer getResponse() {
        return null;
    }
}
