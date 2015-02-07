package org.blockserver;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.blockserver.player.PlayerDatabase;
import org.blockserver.ui.ConsoleOut;

public class ServerBuilder{
	private InetAddress address;
	private int port = -1;
	private String serverName = null;
	private ConsoleOut out = null;
	private File includePath = null;
	private PlayerDatabase playerDb = null;
	private File modulePath = null;
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
	public ServerBuilder setServerName(String name){
		serverName = name;
		return this;
	}
	public ServerBuilder setConsoleOut(ConsoleOut out){
		this.out = out;
		return this;
	}
	public ServerBuilder setIncludePath(File dir){
		this.includePath = dir;
		dir.mkdirs();
		return this;
	}
	public ServerBuilder setPlayerDatabase(PlayerDatabase db){
		playerDb = db;
		return this;
	}
	public ServerBuilder setModulePath(File path){
		modulePath = path;
		return this;
	}

	public Server build(){
		validate(port != -1, "port");
		validate(serverName != null, "serverName");
		validate(out != null, "out");
		validate(includePath != null, "includePath");
		validate(playerDb != null, "playerDb");
		validate(modulePath != null, "modulePath");
		return new Server(address, port, serverName, out, playerDb, modulePath);
	}
	private void validate(boolean bool, String field){
		if(!bool){
			throw new IllegalStateException("Field ServerBuilder." + field
					+ " not initialized!");
		
		}
	}
}
