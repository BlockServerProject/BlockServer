package org.blockserver.network.protocol.pocket;

import java.net.SocketAddress;

public class PocketSession{
	private SocketAddress addr;
	public PocketSession(SocketAddress addr){
		this.addr = addr;
	}
	public SocketAddress getAddress(){
		return addr;
	}
}
