package org.blockserver.network.bridge;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.ArrayList;

import org.blockserver.Server;

public class NonBlockUDPSocket extends Thread{
	private UDPBridge udp;
	private SocketAddress addr;
	private DatagramSocket socket;
	private ArrayList<DatagramPacket> receivedPacketQueue = new ArrayList<DatagramPacket>();
	private boolean running;
	public NonBlockUDPSocket(UDPBridge udp, SocketAddress address){
		this.udp = udp;
		addr = address;
		start();
	}
	@Override
	public void run(){
		setName("UDPSocket");
		try{
			socket = new DatagramSocket(null);
			socket.setBroadcast(true);
			socket.setSendBufferSize(1024 * 1024 * 8); // from PocketMine
			socket.setReceiveBufferSize(1024 * 1024); // from PocketMine
			try{
				socket.bind(addr);
			}
			catch(BindException e){
				getServer().getLogger().fatal("Unable to bind to %s!", addr.toString());
				throw new RuntimeException(e);
			}
			socket.setSoTimeout(0);
			while(running){
				DatagramPacket pk = new DatagramPacket(new byte[1024 * 1024], 1024 * 1024);
				socket.receive(pk);
				synchronized(receivedPacketQueue){
					receivedPacketQueue.add(pk);
				}
			}
			socket.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public DatagramPacket receive(){
		if(receivedPacketQueue.isEmpty()){
			return null;
		}
		synchronized(receivedPacketQueue){
			return receivedPacketQueue.remove(0);
		}
	}
	public boolean send(byte[] buffer, SocketAddress addr){
		try{
			socket.send(new DatagramPacket(buffer, buffer.length, addr));
			return true;
		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	public Server getServer(){
		return udp.getServer();
	}
	public void stop(boolean join){
		running = false;
		if(join){
			try{
				join();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
