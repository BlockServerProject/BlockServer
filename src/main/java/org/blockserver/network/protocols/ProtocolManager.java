package org.blockserver.network.protocols;

import java.util.ArrayList;

import org.blockserver.network.protocols.mcpe.generic.RaknetProtocol;

public class ProtocolManager{
	private ArrayList<Protocol> protocols = new ArrayList<Protocol>();
	private ArrayList<ProtocolSession> sessions = new ArrayList<ProtocolSession>();
	public ProtocolManager(){
		protocols.add(new RaknetProtocol());
	}
}
