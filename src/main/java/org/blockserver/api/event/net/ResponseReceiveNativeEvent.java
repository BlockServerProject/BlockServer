package org.blockserver.api.event.net;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.player.Player;

public class ResponseReceiveNativeEvent extends NativeEvent{
	private Player player;
	private InternalRequest request;

	public ResponseReceiveNativeEvent(Player player, InternalRequest request){
		this.player = player;
		this.request = request;
	}

	public Player getPlayer(){
		return player;
	}

	public InternalRequest getRequest(){
		return request;
	}

	public void setRequest(InternalRequest request){
		this.request = request;
	}
}
