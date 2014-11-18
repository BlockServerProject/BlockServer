package org.blockserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.blockserver.ui.ConsoleOut;

public class ServerBuilder{
	private InetAddress address;
	private int port = -1;
	private ConsoleOut out = null;
	public ServerBuilder(){
		try{
			address = InetAddress.getByName("localhost");
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
	}

	public ServerBuilder setAddress(InetAddress ip){
		address = ip;
		return this;
	}
	public ServerBuilder setPort(int port){
		this.port = port;
		return this;
	}
	public ServerBuilder setConsoleOut(ConsoleOut out){
		this.out = out;
		return this;
	}

	public Server build(){
		validate(port != -1, "port");
		validate(out != null, "out");
		return new Server(address, port, out);
	}
	private void validate(boolean bool, String field){
		if(!bool){
			throw new IllegalStateException("Field ServerBuilder." + field
					+ " not initialized!");
		}
	}
}
