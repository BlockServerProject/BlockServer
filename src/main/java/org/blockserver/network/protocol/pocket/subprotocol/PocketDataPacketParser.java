package org.blockserver.network.protocol.pocket.subprotocol;

import java.util.HashMap;

import org.blockserver.Server;

public class PocketDataPacketParser{
	private HashMap<Byte, Class<? extends PocketDataPacket>> packets = new HashMap<Byte, Class<? extends PocketDataPacket>>();
	private Server server;
	public PocketDataPacketParser(Server server){
		this.server = server;
	}
	public void add(Byte pid, Class<? extends PocketDataPacket> clazz){
		packets.put(pid, clazz);
	}
	public PocketDataPacket parsePacket(byte[] buffer){
		try{
			PocketDataPacket pk = packets.get(buffer[0]).newInstance();
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
