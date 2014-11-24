package org.blockserver.network.protocol;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.blockserver.Server;
import org.blockserver.network.WrappedPacket;

public class ProtocolManager{
	private Server server;
	private ArrayList<Protocol> protocols = new ArrayList<Protocol>();
	private HashMap<SocketAddress, ProtocolSession> sessions = new HashMap<SocketAddress, ProtocolSession>();
	public ProtocolManager(Server server){
		this.server = server;
	}
	public void addProtocol(Protocol protocol){
		protocols.add(protocol);
	}
	public void handlePacket(WrappedPacket pk){
		System.out.println("[ProtocolManager] Handling packet from " + pk.getAddress().toString());
		if(sessions.containsKey(pk.getAddress())){
			getServer().getLogger().debug("Handling packet by session %s", pk.getAddress());
			sessions.get(pk.getAddress()).handlePacket(pk);
		}
		else{
			for(Protocol ptc: protocols){
				ProtocolSession ps = ptc.openSession(pk);
				if(ps != null){
					getServer().getLogger().debug("Opening session %s", pk.getAddress());
					sessions.put(pk.getAddress(), ps);
					break;
				}
			}
		}
	}
	public boolean closeSession(SocketAddress address){
		return sessions.remove(address) != null;
	}
	public Server getServer(){
		return server;
	}
}
