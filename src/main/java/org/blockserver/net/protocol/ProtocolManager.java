/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.net.protocol;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.blockserver.Server;

public class ProtocolManager{
	private Server server;
	private ArrayList<Protocol> protocols = new ArrayList<>();
	private HashMap<SocketAddress, ProtocolSession> sessions = new HashMap<>();
	public ProtocolManager(Server server){
		this.server = server;
	}
	public void addProtocol(Protocol protocol){
		protocols.add(protocol);
		server.getLogger().info("Accepting protocol %s: %s", protocol.getClass().getSimpleName(), protocol.getDescription());
	}
	public void handlePacket(WrappedPacket pk){
		if(sessions.containsKey(pk.getAddress())){
			sessions.get(pk.getAddress()).handlePacket(pk);
		}else{
			for(Protocol ptc : protocols){
				ProtocolSession ps = ptc.openSession(pk);
				if(ps != null){
					sessions.put(pk.getAddress(), ps);
					break;
				}
			}
		}
	}
	public Protocol getProtocol(Class<? extends Protocol> clazz){
		for(Protocol protocol : protocols){
			if(protocol.getClass().getSimpleName().equals(clazz.getSimpleName())){
				return protocol;
			}
		}
		return null;
	}
	/**
	 * This is an internal function. Do <b>NOT</b> use this method from outside.
	 * @param address the address that the session to be closed is connected from
	 * @return whether there is a session with such address
	 */
	public boolean closeSession(SocketAddress address){
		return sessions.remove(address) != null;
	}
	public Server getServer(){
		return server;
	}
}
