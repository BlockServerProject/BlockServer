package org.blockserver.network.protocol.pocket.subprotocol;

import java.util.HashMap;

import org.blockserver.Server;
import org.blockserver.network.protocol.pocket.PocketProtocol;

public class PocketSubprotocolManager{
	private PocketProtocol protocol;
	private HashMap<Integer, PocketSubprotocol> subs = new HashMap<Integer, PocketSubprotocol>();
	public PocketSubprotocolManager(PocketProtocol protocol){
		this.protocol = protocol;
	}
	public void registerSubprotocol(PocketSubprotocol sub){
		subs.put(sub.getSubprotocolVersionId(), sub);
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
