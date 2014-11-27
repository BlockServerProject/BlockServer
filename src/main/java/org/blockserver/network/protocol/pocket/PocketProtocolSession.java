package org.blockserver.network.protocol.pocket;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import org.blockserver.Server;
import org.blockserver.network.WrappedPacket;
import org.blockserver.network.bridge.NetworkBridge;
import org.blockserver.network.protocol.ProtocolManager;
import org.blockserver.network.protocol.ProtocolSession;
import org.blockserver.network.protocol.pocket.raknet.McpeLoginPacket;
import org.blockserver.network.protocol.pocket.raknet.RaknetIncompatibleProtocolVersion;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionReply1;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionReply2;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionRequest1;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionRequest2;
import org.blockserver.network.protocol.pocket.raknet.RaknetReceivedCustomPacket;
import org.blockserver.network.protocol.pocket.raknet.RaknetUnconnectedPing;
import org.blockserver.network.protocol.pocket.raknet.RaknetUnconnectedPong;
import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocol;
import org.blockserver.network.protocol.pocket.subprotocol.PocketSubprotocolManager;
import org.blockserver.player.Player;

public class PocketProtocolSession implements ProtocolSession, PocketProtocolConstants{
	private ProtocolManager mgr;
	private NetworkBridge bridge;
	private SocketAddress addr;
	private PocketProtocol pocket;
	private PocketSubprotocolManager subprotocols;
	private long clientId;
	private short mtu;
	private PocketSubprotocol subprot = null;
	private Player player = null;
	public PocketProtocolSession(ProtocolManager mgr, NetworkBridge bridge, SocketAddress addr, PocketProtocol pocket){
		this.mgr = mgr;
		this.bridge = bridge;
		this.addr = addr;
		this.pocket = pocket;
		subprotocols = pocket.getSubprotocols();
	}
	public SocketAddress getAddress(){
		return addr;
	}

	@Override
	public void handlePacket(WrappedPacket pk){
		ByteBuffer bb = pk.bb();
		byte pid = bb.get();
		if(RAKNET_CUSTOM_PACKET_MIN <= pid && pid <= RAKNET_CUSTOM_PACKET_MAX){
			handleCustomPacket(bb);
		}
		else{
			switch(pid){
				case RAKNET_BROADCAST_PING_1:
				case RAKNET_BROADCAST_PING_2:
					replyToBroadcastPing(bb);
					break;
				case RAKNET_OPEN_CONNECTION_REQUEST_1:
					replyToRequest1(bb);
					break;
				case RAKNET_OPEN_CONNECTION_REQUEST_2:
					replyToRequest2(bb);
					break;
			}
		}
	}

	private void replyToBroadcastPing(ByteBuffer bb){
		RaknetUnconnectedPing ping = new RaknetUnconnectedPing(bb);
		RaknetUnconnectedPong pong = new RaknetUnconnectedPong(ping.pingId, SERVER_ID, ping.magic, getServer().getServerName());
		sendPacket(pong.getBuffer());
	}

	private void replyToRequest1(ByteBuffer bb){
		RaknetOpenConnectionRequest1 req1 = new RaknetOpenConnectionRequest1(bb);
		if(req1.raknetVersion != RAKNET_PROTOCOL_VERSION){
			sendIncompatibility(req1.magic);
		}
		RaknetOpenConnectionReply1 rep1 = new RaknetOpenConnectionReply1(req1.magic, req1.payloadLength /* + 18 */);
		sendPacket(rep1.getBuffer());
	}
	private void sendIncompatibility(byte[] magic){
		RaknetIncompatibleProtocolVersion ipv = new RaknetIncompatibleProtocolVersion(magic, SERVER_ID);
		sendPacket(ipv.getBuffer());
		closeSession();
	}

	private void replyToRequest2(ByteBuffer bb){
		RaknetOpenConnectionRequest2 req2 = new RaknetOpenConnectionRequest2(bb);
		clientId = req2.clientId;
		mtu = req2.mtu;
		RaknetOpenConnectionReply2 rep2 = new RaknetOpenConnectionReply2(req2.magic, req2.serverPort, mtu);
		sendPacket(rep2.getBuffer());
	}

	private void handleCustomPacket(ByteBuffer bb){
		RaknetReceivedCustomPacket cp = new RaknetReceivedCustomPacket(bb);
		for(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket pk: cp.packets){
			handleDataPacket(pk);
		}
	}
	private void handleDataPacket(RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket pk){
		if(pk.buffer[0] == MC_LOGIN_PACKET){
			McpeLoginPacket lp = new McpeLoginPacket(ByteBuffer.wrap(pk.buffer));
			subprot = subprotocols.findProtocol(lp.protocol1, lp.protocol2);
			if(subprot == null){
				String version = Integer.toString(lp.protocol1);
				if(lp.protocol2 != lp.protocol1){
					version += ", #" + Integer.toString(lp.protocol2);
				}
				disconnect("Unsupported protocol version(s) #" + version);
			}
			else{
				player = getServer().newSession(this);
			}
		}
		else if(subprot == null){
			return; // TODO handle
		}
		else{
			subprot.readDataPacket(pk);
		}
	}


	public short getMtu(){
		return mtu;
	}
	public long getClientId(){
		return clientId;
	}
	public Server getServer(){
		return bridge.getServer();
	}

	public void sendPacket(byte[] buffer){
		bridge.send(buffer, getAddress());
	}
	public void closeSession(){
		mgr.closeSession(getAddress());
	}
	public void disconnect(String reason){
		// TODO
	}
	public PocketProtocol getPocketProtocol(){
		return pocket;
	}
	public Player getPlayer(){
		return player;
	}
}
