package org.blockserver.network.protocol.pocket.raknet;

import org.blockserver.network.protocol.pocket.PocketProtocolConstants;

public class PingOpenConnections implements RakNetPacket {
	
	public final static byte PID = PocketProtocolConstants.RAKNET_BROADCAST_PONG_1;
	public long pingID;
	public long serverID;
	public final static byte[] MAGIC = ;
	public String identifier;
	
	public PingOpenConnections(String broadcastName, long serverID, long pingID){
		
	}

}
