package org.blockserver.network;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;

import org.blockserver.Server;

public class UDPSocket{
	public boolean connected = false;
	private String ip;
	private int port;
//	private byte[] buffer = new byte[65535]; // unused?
	private DatagramSocket sck;
	private Server server;

	public UDPSocket(Server server) throws SocketException{
		this(server, "0.0.0.0");
	}
	public UDPSocket(Server server, String ip) throws SocketException{
		this(server, ip, 19132);
	}
	public UDPSocket(Server server, int port) throws SocketException{
		this(server, "0.0.0.0", port);
	}
	public UDPSocket(Server server, String ip, int port) throws SocketException{
		this.server = server;
		this.ip = ip;
		this.port = port;
		connect();
	}

	public synchronized void connect() throws SocketException{
		if(!connected){
			sck = new DatagramSocket(null);
			sck.setBroadcast(true);
			sck.setSendBufferSize(65535);
			sck.setReceiveBufferSize(65535);
			try{
				sck.bind(new InetSocketAddress(ip, port));
				connected = true;
				sck.setSoTimeout(0);
			} catch(BindException e){
				server.getLogger().fatal("COULD NOT BIND TO PORT! - Perhaps something is running on: "+port+"?");
				System.exit(1);
			}

		}
		else{
			server.getLogger().error("The socket is already created.");
		}
	}

	public int sendTo(byte[] buffer, String ip, int port) throws IOException{
		sck.send(new DatagramPacket(buffer, buffer.length, new InetSocketAddress(ip, port)));
		return buffer.length;
	}
	public int sendTo(byte[] buffer, InetSocketAddress address) throws IOException{
			sck.send(new DatagramPacket(buffer, buffer.length, address));
			return buffer.length;
	}
	public int send(DatagramPacket pck) throws IOException{
		sck.send(pck);
		return pck.getData().length;
	}

	public DatagramPacket receive() throws SocketException, IOException{
		byte[] buffer = new byte[1536];
		DatagramPacket pck = new DatagramPacket(buffer, 1536);
		sck.receive(pck);
		pck.setData(Arrays.copyOf(buffer, pck.getLength()));
		return pck;
	}

	public void close(){
		if(connected){
			connected = false;
			sck.disconnect();
			sck.close();
		}
	}
}
