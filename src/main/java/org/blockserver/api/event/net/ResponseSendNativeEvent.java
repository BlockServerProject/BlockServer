package org.blockserver.api.event.net;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.player.Player;

public class ResponseSendNativeEvent extends NativeEvent{
	private Player player;
	private InternalResponse response;

	public ResponseSendNativeEvent(Player player, InternalResponse response){
		this.player = player;
		this.response = response;
	}

	public Player getPlayer(){
		return player;
	}

	public InternalResponse getResponse(){
		return response;
	}

	public void setResponse(InternalResponse response){
		this.response = response;
	}
}
