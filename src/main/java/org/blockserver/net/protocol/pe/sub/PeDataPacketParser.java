package org.blockserver.net.protocol.pe.sub;

import java.util.HashMap;

import org.blockserver.Server;

public class PeDataPacketParser{
	private HashMap<Byte, Class<? extends PeDataPacket>> packets = new HashMap<>();
	private Server server;
	public PeDataPacketParser(Server server){
		this.server = server;
	}
	public void add(Byte pid, Class<? extends PeDataPacket> clazz){
		packets.put(pid, clazz);
	}
	public PeDataPacket parsePacket(byte[] buffer){
		try{
			PeDataPacket pk = packets.get(buffer[0]).newInstance();
			pk.decode(buffer);
			return pk;
		}
		catch(NullPointerException e){
			server.getLogger().warning("Unknown data packet ID %d", (int) buffer[0]);
			return null;
		}
		catch(InstantiationException | IllegalAccessException e){
			e.printStackTrace();
			return null;
		}
	}
}
