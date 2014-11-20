package org.blockserver.network.protocol.pocket;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import org.blockserver.network.WrappedPacket;
import org.blockserver.network.bridge.NetworkBridge;
import org.blockserver.network.protocol.ProtocolManager;
import org.blockserver.network.protocol.ProtocolSession;
import org.blockserver.network.protocol.pocket.raknet.RaknetIncompatibleProtocolVersion;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionReply1;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionReply2;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionRequest1;
import org.blockserver.network.protocol.pocket.raknet.RaknetOpenConnectionRequest2;

public class PocketProtocolSession implements ProtocolSession, PocketProtocolConstants{
	private ProtocolManager mgr;
	private NetworkBridge bridge;
	private SocketAddress addr;
	private long clientId;
	private short mtu;
	public PocketProtocolSession(ProtocolManager mgr, NetworkBridge bridge, SocketAddress addr){
		this.mgr = mgr;
		this.bridge = bridge;
		this.addr = addr;
	}
	public SocketAddress getAddress(){
		return addr;
	}

	public void handlePacket(WrappedPacket pk){
		ByteBuffer bb = pk.bb();
		byte pid = bb.get();
		switch(pid){
			case RAKNET_OPEN_CONNECTION_REQUEST_1:
				replyToRequest1(bb);
				break;
			case RAKNET_OPEN_CONNECTION_REQUEST_2:
				replyToRequest2(bb);
				break;
		}
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

	public short getMtu(){
		return mtu;
	}
	public long getClientId(){
		return clientId;
	}

	public void sendPacket(byte[] buffer){
		bridge.send(buffer, getAddress());
	}
	public void closeSession(){
		mgr.closeSession(getAddress());
	}
}
