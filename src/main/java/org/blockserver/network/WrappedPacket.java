package org.blockserver.network;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.blockserver.network.bridge.NetworkBridge;

public class WrappedPacket{
	private ByteBuffer bb;
	private SocketAddress addr;
	private NetworkBridge bridge;
	public WrappedPacket(byte[] buffer, SocketAddress addr, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
		this.addr = addr;
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
