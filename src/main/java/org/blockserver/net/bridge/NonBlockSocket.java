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

import org.blockserver.Server;
import org.blockserver.net.protocol.WrappedPacket;

import java.io.IOException;
import java.util.ArrayList;

public abstract class NonBlockSocket extends Thread{
	private boolean isRunning = true;
	private boolean terminated = false;
	private final ArrayList<WrappedPacket> receiveQueue = new ArrayList<>(), sendQueue = new ArrayList<>();
	@Override
	public void run(){
		while(isRunning){
			while(!sendQueue.isEmpty()){
				WrappedPacket packet;
				synchronized(sendQueue){
					packet = sendQueue.remove(0);
				}
				send(packet);
			}
			WrappedPacket pk = receive_();
			synchronized(receiveQueue){
				receiveQueue.add(pk);
			}
		}
		try{
			finalizeStuffs();
		}catch(IOException e){
			e.printStackTrace();
		}
		terminated = true;
	}
	public abstract void send(WrappedPacket pk);
	public abstract WrappedPacket receive_();
	@SuppressWarnings("RedundantThrows")
	public abstract void finalizeStuffs() throws IOException;
	public final WrappedPacket receive(){
		synchronized(receiveQueue){
			return receiveQueue.isEmpty() ? null : receiveQueue.remove(0);
		}
	}
	public void close(){
		close(true);
	}
	public void close(boolean wait){
		isRunning = false;
		if(wait){
			while(!terminated);
		}
	}
	public abstract NetworkBridge getBridge();
	public Server getServer(){
		return getBridge().getServer();
	}
}
