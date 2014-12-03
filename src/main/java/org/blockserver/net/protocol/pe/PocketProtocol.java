package org.blockserver.net.protocol.pe;

import org.blockserver.Server;
import org.blockserver.net.bridge.UDPBridge;
import org.blockserver.net.protocol.Protocol;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.raknet.RaknetUnconnectedPing;
import org.blockserver.net.protocol.pe.raknet.RaknetUnconnectedPong;
import org.blockserver.net.protocol.pe.sub.PocketSubprotocolManager;

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
		System.out.println("Handling packet from " + pk.getAddress().toString());
		if(pk.getBridge() instanceof UDPBridge){
			byte pid = pk.bb().get(0);
			if(pid == RAKNET_BROADCAST_PING_1 || pid == RAKNET_BROADCAST_PING_2){
				advertize(pk);
				return null;
			}
			else if(pid == RAKNET_OPEN_CONNECTION_REQUEST_1){
				PocketProtocolSession session = new PocketProtocolSession(protocols, pk.getBridge(), pk.getAddress(), this);
				session.handlePacket(pk);
				return session;
			}
		}
		return null;
	}
	private void advertize(WrappedPacket pk){
		RaknetUnconnectedPing ping = new RaknetUnconnectedPing(pk.bb());
		RaknetUnconnectedPong pong = new RaknetUnconnectedPong(ping.pingId, SERVER_ID, ping.magic, server.getServerName());
		byte[] out;
		pk.getBridge().send(out = pong.getBuffer(), pk.getAddress());
		getServer().getLogger().buffer("Advertizement outgoing buffer ", out, "");
	}
	public Server getServer(){
		return server;
	}
	public PocketSubprotocolManager getSubprotocols(){
		return subprotocols;
	}
	@Override
	public String getDescription(){
		return "Accepts connections from MCPE devices";
	}
}
