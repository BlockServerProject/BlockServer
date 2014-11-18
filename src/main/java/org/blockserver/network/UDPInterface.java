package org.blockserver.network;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.blockserver.Server;
import org.blockserver.utils.Callable;

public class UDPInterface{
	private Server server;
	private InetAddress ip;
	private int port;
	private DatagramSocket sk;
	private boolean running = true;
	public UDPInterface(Server server, InetAddress ip, int port) throws SocketException{
		this.server = server;
		this.ip = ip;
		this.port = port;
		try{
			this.server.registerShutdownFunction(new Callable(this, "shutdown"));
		}
		catch(NoSuchMethodException e){
			e.printStackTrace();
		}
	}
	public void start(){
		try{
			sk = new DatagramSocket(null);
			sk.setBroadcast(true);
			sk.setSendBufferSize(65535);
			sk.setReceiveBufferSize(65535);
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			try{
				sk.bind(addr);
			}
			catch(BindException e){
				server.getLogger().fatal("Cannot bind to %s. Is another server running at that port?", addr.toString());
			}
			sk.setSoTimeout(0);
		}
		catch(SocketException e){
			e.printStackTrace();
			return;
		}
		try{
			loop();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	private void loop() throws IOException{
		while(running){
			DatagramPacket pk = new DatagramPacket(new byte[65535], 65535);
			sk.receive(pk);
			WrappedPacket wp = new WrappedPacket(pk.getData(),
					WrappedPacket.TRAFFIC_UDP, pk.getSocketAddress());
			
		}
	}
	public DatagramSocket getSocket(){
		return sk;
	}
	public void shutdown(){
		running = false;
		sk.close();
	}
}
