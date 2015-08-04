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

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

import org.blockserver.Server;
import org.blockserver.utils.Callable;

@Deprecated // deprecate it soon
public class NonBlockUDPSocket_old extends Thread{
	private UDPBridge udp;
	private SocketAddress addr;
	private DatagramSocket socket;
	private final ArrayList<DatagramPacket> receivedPacketQueue = new ArrayList<>();
	private boolean running = true; // I forget to set this to default true every time and go into strange bugs!
	public NonBlockUDPSocket_old(UDPBridge udp, SocketAddress address){
		this.udp = udp;
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
			getServer().addShutdownFunction(new Callable(this, "_stop"));
			while(running){
				byte[] buffer = new byte[1024 * 1024];
				DatagramPacket pk = new DatagramPacket(buffer, buffer.length);
				socket.receive(pk);
				pk.setData(Arrays.copyOf(buffer, pk.getLength()));
				synchronized(receivedPacketQueue){
					receivedPacketQueue.add(pk);
				}
			}
			socket.close();
		}catch(IOException | NoSuchMethodException e){
			e.printStackTrace();
		}
	}
	public DatagramPacket receive(){
		if(receivedPacketQueue.isEmpty()){
			return null;
		}
		synchronized(receivedPacketQueue){
			return receivedPacketQueue.remove(0);
		}
	}
	public boolean send(byte[] buffer, SocketAddress addr){
		try{
			socket.send(new DatagramPacket(buffer, buffer.length, addr));
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	public Server getServer(){
		return udp.getServer();
	}
	public void _stop(){
		stop(false);
	}
	public void stop(boolean join){
		running = false;
		if(join){
			try{
				join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
