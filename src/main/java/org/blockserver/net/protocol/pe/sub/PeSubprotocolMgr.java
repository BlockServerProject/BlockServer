package org.blockserver.net.protocol.pe.sub;

import java.util.HashMap;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.PeProtocol;

public class PeSubprotocolMgr{
	private PeProtocol protocol;
	private HashMap<Integer, PeSubprotocol> subs = new HashMap<>();
	public PeSubprotocolMgr(PeProtocol protocol){
		this.protocol = protocol;
	}
	public void registerSubprotocol(PeSubprotocol sub){
		subs.put(sub.getSubprotocolVersionId(), sub);
		getServer().getLogger().info("PocketProtocol now accepts MCPE protocol %s", sub.getSubprotocolName());
	}
	public Server getServer(){
		return protocol.getServer();
	}
	public PeSubprotocol findProtocol(int... versions){
		for(int version: versions){
			PeSubprotocol sub = subs.get(version);
			if(sub != null){
				return sub;
			}
		}
		return null;
	}
}
