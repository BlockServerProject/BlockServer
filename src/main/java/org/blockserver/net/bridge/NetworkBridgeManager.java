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
package org.blockserver.net.bridge;

import java.util.ArrayList;

import org.blockserver.Server;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.ticker.CallableTask;

public class NetworkBridgeManager{
	private Server server;
	private ArrayList<NetworkBridge> bridges = new ArrayList<>();
	public NetworkBridgeManager(Server server){
		this.server = server;
		try{
			server.getTicker().addRepeatingTask(new CallableTask(this, "tick"), 1);
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}
	}
	public void addBridge(NetworkBridge bridge){
		bridges.add(bridge);
		server.getLogger().info("Started network bridge %s: %s", bridge.getClass().getSimpleName(), bridge.getDescription());
	}
	public Server getServer(){
		return server;
	}

	public void tick(){
		for(NetworkBridge bridge : bridges){
			WrappedPacket pk;
			while((pk = bridge.receive()) != null){
				server.getProtocols().handlePacket(pk);
			}
		}
	}
}
