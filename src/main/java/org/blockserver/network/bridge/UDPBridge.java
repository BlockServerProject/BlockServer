package org.blockserver.network.bridge;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;

import org.blockserver.Server;
import org.blockserver.network.WrappedPacket;

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
	@Override
	public WrappedPacket receive(){
		DatagramPacket dp = socket.receive();
		byte[] data = dp.getData();
		mgr.getServer().getLogger().info("Received packet: %s", Arrays.toString(data));
		WrappedPacket wp = new WrappedPacket(data, dp.getSocketAddress(), this);
		return wp;
	}
	public boolean send(byte[] buffer, SocketAddress addr){
		mgr.getServer().getLogger().info("Sending buffer: %s", Arrays.toString(buffer));
		return socket.send(buffer, addr);
	}
	public void stop(){
		socket.stop(true);
	}
	public Server getServer(){
		return mgr.getServer();
	}
}
