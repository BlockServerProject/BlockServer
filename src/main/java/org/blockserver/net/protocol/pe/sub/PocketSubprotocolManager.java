package org.blockserver.net.protocol.pe.sub;

import java.util.HashMap;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.PocketProtocol;

public class PocketSubprotocolManager{
	private PocketProtocol protocol;
	private HashMap<Integer, PocketSubprotocol> subs = new HashMap<Integer, PocketSubprotocol>();
	public PocketSubprotocolManager(PocketProtocol protocol){
		this.protocol = protocol;
	}
	public void registerSubprotocol(PocketSubprotocol sub){
		subs.put(sub.getSubprotocolVersionId(), sub);
		getServer().getLogger().info("PocketProtocol now accepts MCPE protocol %s", sub.getSubprotocolName());
	}
	public Server getServer(){
		return protocol.getServer();
	}
	public PocketSubprotocol findProtocol(int... versions){
		for(int version: versions){
			PocketSubprotocol sub = subs.get(version);
			if(sub != null){
				return sub;
			}
		}
		return null;
	}
}
