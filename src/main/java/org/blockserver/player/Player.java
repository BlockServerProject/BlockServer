package org.blockserver.player;

import org.blockserver.Server;
import org.blockserver.network.protocol.ProtocolSession;

public class Player{
	private Server server;
	private ProtocolSession protocol;
	private PlayerLoginInfo login;
	public Player(ProtocolSession protocol, PlayerLoginInfo login){
		this.protocol = protocol;
		this.login = login;
		server = protocol.getServer();
		openDatabase();
	}
	private void openDatabase(){
		// TODO
	}
	public ProtocolSession getProtocolSession(){
		return protocol;
	}
	public String getUsername(){
		return login.username;
	}
	public Server getServer(){
		return server;
	}
}
