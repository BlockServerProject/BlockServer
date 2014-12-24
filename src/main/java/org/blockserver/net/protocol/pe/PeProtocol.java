package org.blockserver.net.protocol.pe;

import org.blockserver.Server;
import org.blockserver.net.bridge.UDPBridge;
import org.blockserver.net.protocol.Protocol;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.login.RaknetUnconnectedPing;
import org.blockserver.net.protocol.pe.login.RaknetUnconnectedPong;
import org.blockserver.net.protocol.pe.sub.PeSubprotocolMgr;

public class PeProtocol extends Protocol implements PeProtocolConst{
	private Server server;
	private ProtocolManager protocols;
	private PeSubprotocolMgr subprotocols;
	public PeProtocol(Server server){
		this.server = server;
		protocols = server.getProtocols();
		subprotocols = new PeSubprotocolMgr(this);
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
				PeProtocolSession session = new PeProtocolSession(protocols, pk.getBridge(), pk.getAddress(), this);
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
	public PeSubprotocolMgr getSubprotocols(){
		return subprotocols;
	}
	@Override
	public String getDescription(){
		return "Accepts connections from MCPE devices";
	}
}
