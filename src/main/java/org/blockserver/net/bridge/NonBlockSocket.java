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
