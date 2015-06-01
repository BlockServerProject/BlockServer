package org.blockserver.player;

import org.blockserver.Server;
import org.blockserver.api.event.net.ResponseReceiveNativeEvent;
import org.blockserver.api.event.net.ResponseSendNativeEvent;
import org.blockserver.net.internal.request.DisconnectRequest;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.internal.request.PingRequest;
import org.blockserver.net.internal.response.ChatResponse;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.internal.response.PingResponse;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.utils.PositionDoublePrecision;

public class Player{
	private Server server;
	private ProtocolSession protocol;
	private PlayerLoginInfo login;
	private PositionDoublePrecision location;
	private int entityID;
	private int gm;
	private String nickname;
	public Player(ProtocolSession protocol, PlayerLoginInfo login){
		this.protocol = protocol;
		this.login = login;
		server = protocol.getServer();
		entityID = login.entityID;
		openDatabase();
	}
	private void openDatabase(){
		//PlayerData data = server.getPlayerDatabase().readPlayer(this);
		location = new PositionDoublePrecision(0, 64, 0);
		gm = 1; //CREATIVE, Also DUMMY
		// TODO read the data
	}
	private void sendResponse(InternalResponse response){
		ResponseSendNativeEvent event = new ResponseSendNativeEvent(this, response);
		if(!getServer().getAPI().handleEvent(event)){
			return;
		}
		protocol.sendResponse(event.getResponse());
	}
	public void handleRequest(InternalRequest request){
		ResponseReceiveNativeEvent event = new ResponseReceiveNativeEvent(this, request);
		getServer().getAPI().handleEvent(event);
		request = event.getRequest();
		if(request instanceof PingRequest){
			PingResponse pingResponse = new PingResponse();
			pingResponse.pingId = ((PingRequest) request).pingId;
			sendResponse(pingResponse);
		}else if(request instanceof DisconnectRequest){
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
	public PositionDoublePrecision getLocation(){
		return location;
	}
	public String getNickname(){
		return nickname;
	}
	public String setNickname(String newNick){
		String old = nickname;
		nickname = newNick;
		// TODO change nametag when players spawn
		return old;
	}
	public void sendMessage(String message){
		ChatResponse chat = new ChatResponse();
		chat.message = message;
		sendResponse(chat);
	}
}
