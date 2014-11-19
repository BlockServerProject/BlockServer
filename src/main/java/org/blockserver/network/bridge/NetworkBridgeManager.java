package org.blockserver.network.bridge;

import org.blockserver.Server;

public class NetworkBridgeManager{
	private Server server;
	private UDPBridge udp;
	public NetworkBridgeManager(Server server){
		this.server = server;
		this.udp = new UDPBridge(this);
	}
	public UDPBridge getUDPBridge(){
		return udp;
	}
	public Server getServer(){
		return server;
	}
}
