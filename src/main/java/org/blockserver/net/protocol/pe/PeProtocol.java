package org.blockserver.net.protocol.pe;

import org.blockserver.Server;
import org.blockserver.net.bridge.udp.UDPBridge;
import org.blockserver.net.protocol.Protocol;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.raknet.ConnectedPingPacket;
import org.blockserver.net.protocol.pe.raknet.UnconnectedPingPacket;
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
		server.getLogger().debug("Handling packet from " + pk.getAddress().toString());
		if(pk.getBridge() instanceof UDPBridge){
			byte pid = pk.bb().get(0);
			if(pid == RAKNET_BROADCAST_PING_1 || pid == RAKNET_BROADCAST_PING_2){
				advertize(pk);
				return null;
			}else if(pid == RAKNET_OPEN_CONNECTION_REQUEST_1){
				RakNetProtocolSession session = new RakNetProtocolSession(protocols, pk.getBridge(), pk.getAddress(), this);
				session.handlePacket(pk);
				return session;
			}
		}
		return null;
	}
	private void advertize(WrappedPacket pk){
		ConnectedPingPacket cpp = new ConnectedPingPacket();
		cpp.decode(pk.bb());

		UnconnectedPingPacket upp = new UnconnectedPingPacket();
		upp.pingID = cpp.pingID;
		upp.mcpeIdentifier = "MCPE;";
		upp.identifier = server.getServerName() + ";27;0.11.0;0;10"; //TODO: Get MAX players and current.

		pk.getBridge().send(upp.encode(), pk.getAddress());
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
