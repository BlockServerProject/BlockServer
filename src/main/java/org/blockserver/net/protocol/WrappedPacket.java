package org.blockserver.net.protocol;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.blockserver.net.bridge.NetworkBridge;

public class WrappedPacket{
	private ByteBuffer bb;
	private SocketAddress addr = null;
	private NetworkBridge bridge;
	public Socket socket = null;
	public WrappedPacket(byte[] buffer, SocketAddress addr, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
		this.addr = addr;
		this.bridge = bridge;
	}
	public WrappedPacket(byte[] buffer, Socket socket, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
		this.socket = socket;
		this.bridge = bridge;
	}
	public ByteBuffer bb(){
		return bb;
	}
	public SocketAddress getAddress(){
		return addr;
	}
	public NetworkBridge getBridge(){
		return bridge;
	}
}
