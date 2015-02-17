package org.blockserver.player;

import org.blockserver.Server;
import org.blockserver.net.internal.request.DisconnectRequest;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.internal.request.PingRequest;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.internal.response.PingResponse;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.pe.PeProtocol;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.utils.Position;

import java.util.Dictionary;

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
	private void sendResponse(InternalResponse response){
		protocol.sendResponse(response);
		//TODO: More
	}
	public void handleRequest(InternalRequest request){
		if(request instanceof PingRequest){
			PingResponse pingResponse = new PingResponse();
			pingResponse.pingId = ((PingRequest) request).pingId;
			sendResponse(pingResponse);
		} else if(request instanceof DisconnectRequest){
			DisconnectRequest disconnectRequest = (DisconnectRequest) request;
			getServer().getLogger().info(login.username + "(EID: "+entityID+") ["+getProtocolSession().getAddress().toString()+"] logged out due to: "+disconnectRequest.reason);
			protocol.closeSession("");
			server.getProtocols().closeSession(getProtocolSession().getAddress());
		}
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
