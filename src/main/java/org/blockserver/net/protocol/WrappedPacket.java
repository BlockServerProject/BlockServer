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

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.blockserver.net.bridge.NetworkBridge;

public class WrappedPacket{
	private ByteBuffer bb;
	private SocketAddress addr = null;
	private NetworkBridge bridge;
	public Socket socket = null;
	public WrappedPacket(byte[] buffer, SocketAddress addr, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN);
		this.addr = addr;
		this.bridge = bridge;
	}
	public WrappedPacket(byte[] buffer, Socket socket, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN);
		this.socket = socket;
		this.bridge = bridge;
	}
	public ByteBuffer bb(){
		return bb;
	}
	public SocketAddress getAddress(){
		return addr;
	}
	public NetworkBridge getBridge(){
		return bridge;
	}
}
