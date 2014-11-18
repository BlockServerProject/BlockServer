package org.blockserver.network.protocols.mcpe.generic;

import org.blockserver.network.UDPInterface;
import org.blockserver.network.WrappedPacket;
import org.blockserver.network.protocols.Protocol;
import org.blockserver.network.protocols.ProtocolSession;

public class RaknetProtocol extends Protocol{
	public final static byte RAKNET_OPEN_CONNECTION_REQUEST_1 = 0x05;
	public final static byte RAKNET_OPEN_CONNECTION_REPLY_1 = 0x06;
	public final static byte RAKNET_OPEN_CONNECTION_REQUEST_2 = 0x07;
	public final static byte RAKNET_OPEN_CONNECTION_REPLY_2 = 0x08;
	private UDPInterface itf;
	public RaknetProtocol(){
		
	}
	@Override
	public ProtocolSession openSession(WrappedPacket pk){
		if(pk.getTrafficType() != WrappedPacket.TRAFFIC_UDP){
			return null;
		}
		byte pid = pk.bb().get(0);
		if(pid == RAKNET_OPEN_CONNECTION_REQUEST_1){
			RaknetSession session = new RaknetSession(itf, pk);
			session.handle(pk);
			
		}
		return null;
	}
}
