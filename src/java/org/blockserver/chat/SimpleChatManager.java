package org.blockserver.chat;

import org.blockserver.Server;
import org.blockserver.player.Player;

public class SimpleChatManager extends ChatManager{
	private Server server = null;

	public void initialize(Server server){
		this.server = server;
	}

	@Override
	public Server getServer(){
		return server;
	}

	@Override
	public void handleChat(Player player, String msg){
		broadcastMsg(String.format("<%s> %s", player.getName(), msg));
	}

	@Override
	protected void broadcastMsg(String string){
		for(Player p: server.getConnectedPlayers()){
			p.sendMessage(string);
		}
	}
}
