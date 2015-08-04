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
package org.blockserver.net.protocol.pe.sub;

import java.util.HashMap;

import org.blockserver.Server;

public class PeSubprotocolMgr{
	/*
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
	*/
}
