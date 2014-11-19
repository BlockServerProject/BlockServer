package org.blockserver.network.bridge;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.blockserver.Server;
import org.blockserver.network.protocol.WrappedPacket;

public class UDPBridge extends NetworkBridge{
	private NetworkBridgeManager mgr;
	private NonBlockUDPSocket socket;
	private SocketAddress addr;
	public UDPBridge(NetworkBridgeManager mgr){
		this.mgr = mgr;
		start();
	}
	private void start(){
		addr = new InetSocketAddress(getServer().getAddress(), getServer().getPort());
		socket = new NonBlockUDPSocket(this, addr);
	}
	public WrappedPacket receive(){
		DatagramPacket dp = socket.receive();
		WrappedPacket wp = new WrappedPacket(dp.getData(), dp.getSocketAddress(), this);
		return wp;
	}
	public boolean send(byte[] buffer, SocketAddress addr){
		return socket.send(buffer, addr);
	}
	public void stop(){
		socket.stop(true);
	}
	public Server getServer(){
		return mgr.getServer();
	}
}
