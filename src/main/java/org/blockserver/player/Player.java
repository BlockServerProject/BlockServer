package org.blockserver.player;

import org.blockserver.Server;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.utils.Position;

public class Player{
	private Server server;
	private ProtocolSession protocol;
	private PlayerLoginInfo login;
	private Position location;
	private int entityID;
	private int gm;
	public Player(ProtocolSession protocol, PlayerLoginInfo login){
		this.protocol = protocol;
		this.login = login;
		server = protocol.getServer();
		entityID = login.entityID;
		openDatabase();
	}
	private void openDatabase(){
		//PlayerData data = server.getPlayerDatabase().readPlayer(this);
		location = new Position(0, 64, 0); //Dummy
		gm = 1; //CREATIVE, Also DUMMY
		// TODO read the data
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
	public void kick(String reason){
		close(reason);
	}
	public void close(String reason){
		protocol.closeSession(reason);
	}
	public int getEntityID(){
		return entityID;
	}
	public int getGamemode(){
		return gm;
	}
	public Position getLocation(){
		return location;
	}
}
