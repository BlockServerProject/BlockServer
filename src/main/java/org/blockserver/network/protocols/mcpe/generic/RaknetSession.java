package org.blockserver.network.protocols.mcpe.generic;

import java.net.SocketAddress;

import org.blockserver.network.UDPInterface;
import org.blockserver.network.WrappedPacket;

public class RaknetSession{
	private SocketAddress addr;
	public RaknetSession(UDPInterface server, WrappedPacket pk){
		this(pk.getAddress());
	}
	public RaknetSession(SocketAddress addr){
		this.addr = addr;
	}
	public void handle(WrappedPacket pk){
		
	}
	private void send(byte[] buffer){
		
	}
}
