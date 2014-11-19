package org.blockserver.network.bridge;

import java.util.ArrayList;

import org.blockserver.Server;
import org.blockserver.network.protocol.WrappedPacket;
import org.blockserver.ticker.CallableTask;

public class NetworkBridgeManager{
	private Server server;
	private ArrayList<NetworkBridge> bridges = new ArrayList<NetworkBridge>();
	public NetworkBridgeManager(Server server){
		this.server = server;
		bridges.add(new UDPBridge(this));
		try{
			server.getTicker().addRepeatingTask(new CallableTask(this, "tick"), 1);
		}
		catch(NoSuchMethodException e){
			e.printStackTrace();
		}
	}
	public Server getServer(){
		return server;
	}

	public void tick(){
		for(NetworkBridge bridge: bridges){
			WrappedPacket pk;
			while((pk = bridge.receive()) != null){
				server.getProtocols().handlePacket(pk);
			}
		}
	}
}
