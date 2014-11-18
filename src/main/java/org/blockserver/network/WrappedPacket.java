package org.blockserver.network;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class WrappedPacket{
	public final static int TRAFFIC_UDP = 1;
	public final static int TRAFFIC_TCP = 2;
	private ByteBuffer bb;
	private int trafficType;
	private SocketAddress addr;
	public WrappedPacket(byte[] buffer, int trafficType, SocketAddress addr){
		bb = ByteBuffer.wrap(buffer);
		this.trafficType = trafficType;
		this.addr = addr;
	}
	public ByteBuffer bb(){
		return bb;
	}
	public int getTrafficType(){
		return trafficType;
	}
	public SocketAddress getAddress(){
		return addr;
	}
}
