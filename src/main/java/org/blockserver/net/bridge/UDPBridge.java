package org.blockserver.net.bridge;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.blockserver.Server;
import org.blockserver.net.protocol.WrappedPacket;

public class UDPBridge extends NetworkBridge{
	private NetworkBridgeManager mgr;
	private NonBlockUDPSocket socket;
	private SocketAddress addr;

	public UDPBridge(NetworkBridgeManager mgr){
		this.mgr = mgr;
		addr = new InetSocketAddress(getServer().getAddress(), getServer().getPort());
		start();
	}
	public UDPBridge(NetworkBridgeManager mgr, SocketAddress addr){
		this.mgr = mgr;
		this.addr = addr;
		start();
	}
	private void start(){
		socket = new NonBlockUDPSocket(this, addr);
	}
	@Override
	public WrappedPacket receive(){
		DatagramPacket dp = socket.receive();
		if(dp != null){
			byte[] data = dp.getData();
			WrappedPacket wp = new WrappedPacket(data, dp.getSocketAddress(), this);
			return wp;
		}
		return null;
	}
	@Override
	public boolean send(byte[] buffer, SocketAddress addr){
		return socket.send(buffer, addr);
	}
	public void stop(){
		socket.stop(true);
	}
	@Override
	public Server getServer(){
		return mgr.getServer();
	}
	@Override
	public String getDescription(){
		return "A network connection bridge for UDP.";
	}
}
