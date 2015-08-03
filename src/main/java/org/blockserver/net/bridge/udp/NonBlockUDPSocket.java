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

import org.blockserver.net.bridge.NonBlockSocket;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.utils.Callable;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;

public class NonBlockUDPSocket extends NonBlockSocket{
	private UDPBridge udp;
	private SocketAddress addr;
	private DatagramSocket socket;
	public NonBlockUDPSocket(UDPBridge bridge, SocketAddress address){
		udp = bridge;
		addr = address;
		start();
	}
	@Override
	public void run(){
		setName("UDPSocket");
		udp.getServer().getLogger().info("Binding UDP socket on %s...", addr.toString());
		try{
			socket = new DatagramSocket(null);
			socket.setBroadcast(true);
			socket.setSendBufferSize(1024 * 1024 * 8); // from PocketMine
			socket.setReceiveBufferSize(1024 * 1024); // from PocketMine
			try{
				socket.bind(addr);
			}catch(BindException e){
				getServer().getLogger().fatal("Unable to bind to %s!", addr.toString());
				throw new RuntimeException(e);
			}
			socket.setSoTimeout(0);
			try{
				getServer().addShutdownFunction(new Callable(this, "close"));
			}
			catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}catch(SocketException e){
			e.printStackTrace();
		}
	}
	@Override
	public void send(WrappedPacket pk){
		try{
			byte[] buffer = pk.bb().array();
			if(buffer.length > 1400){
				System.out.println(buffer.length);
			}
			socket.send(new DatagramPacket(buffer, buffer.length, pk.getAddress()));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public WrappedPacket receive_(){
		try{
			byte[] buffer = new byte[1024 * 1024];
			DatagramPacket pk = new DatagramPacket(buffer, buffer.length);
			socket.receive(pk);
			pk.setData(Arrays.copyOf(buffer, pk.getLength()));
			return new WrappedPacket(buffer, new InetSocketAddress(pk.getAddress(), pk.getPort()), udp);

		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void finalizeStuffs(){
		socket.close();
	}
	@Override
	public UDPBridge getBridge(){
		return udp;
	}
}
