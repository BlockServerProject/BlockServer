package org.blockserver.network.protocol.pocket;

import org.blockserver.Server;
import org.blockserver.network.WrappedPacket;
import org.blockserver.network.bridge.UDPBridge;
import org.blockserver.network.protocol.Protocol;
import org.blockserver.network.protocol.ProtocolManager;
import org.blockserver.network.protocol.ProtocolSession;
import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocolManager;

public class PocketProtocol extends Protocol implements PocketProtocolConstants{
	private Server server;
	private ProtocolManager protocols;
	private PocketSubprotocolManager subprotocols;
	public PocketProtocol(Server server){
		this.server = server;
		protocols = server.getProtocols();
		subprotocols = new PocketSubprotocolManager(this);
	}
	@Override
	public ProtocolSession openSession(WrappedPacket pk){
		if(pk.getBridge() instanceof UDPBridge){
			byte pid = pk.bb().get(0);
			if(pid == RAKNET_BROADCAST_PING_1 || pid == RAKNET_BROADCAST_PING_2){
				advertize(pk);
				return null;
			}
			else if(pid == RAKNET_OPEN_CONNECTION_REQUEST_1){
				PocketProtocolSession session = new PocketProtocolSession(protocols, pk.getBridge(), pk.getAddress());
				session.handlePacket(pk);
				return session;
			}
		}
		return null;
	}
	private void advertize(WrappedPacket pk){
		// TODO
	}
	public Server getServer(){
		return server;
	}
	public PocketSubprotocolManager getSubprotocols(){
		return subprotocols;
	}
}
