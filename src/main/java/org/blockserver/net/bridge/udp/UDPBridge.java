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
package org.blockserver.net.bridge.udp;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.bridge.NetworkBridgeManager;
import org.blockserver.net.protocol.WrappedPacket;

public class UDPBridge extends NetworkBridge{
	private NetworkBridgeManager mgr;
	private NonBlockUDPSocket_old socket;
	private SocketAddress addr;

	public UDPBridge(NetworkBridgeManager mgr){
		this.mgr = mgr;
		addr = new InetSocketAddress(getServer().getAddress(), getServer().getPort());
		start();
	}
	public UDPBridge(NetworkBridgeManager mgr, SocketAddress addr){
		this.mgr = mgr;
		this.addr = addr;
		start();
	}
	private void start(){
		socket = new NonBlockUDPSocket_old(this, addr);
	}
	@Override
	public WrappedPacket receive(){
		DatagramPacket dp = socket.receive();
		if(dp != null){
			byte[] data = dp.getData();
			return new WrappedPacket(data, dp.getSocketAddress(), this);
		}
		return null;
	}
	@Override
	public boolean send(byte[] buffer, SocketAddress addr){
		return socket.send(buffer, addr);
	}
	public void stop(){
		socket.stop(true);
	}
	@Override
	public Server getServer(){
		return mgr.getServer();
	}
	@Override
	public String getDescription(){
		return "A network connection bridge for UDP.";
	}
}
