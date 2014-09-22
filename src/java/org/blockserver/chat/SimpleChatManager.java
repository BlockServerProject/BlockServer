package org.blockserver.chat;

import org.blockserver.Server;
import org.blockserver.player.Player;

public class SimpleChatManager extends ChatManager{
	private Server server = null;

	@Override
	public void initialize(Server server){
		this.server = server;
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	public void handlePlayerChat(Player player, String msg){
		if(msg.charAt(0) == '/'){
			player.sudoCommand(msg); // Note: commands are sent raw with a leading slash
		}
		else{
			broadcastMsg(String.format("<%s> %s", player.getName(), msg));
		}
	}
	
	@Override
	protected void broadcastMsg(String msg){
		server.getLogger().info(msg);
		for(Player p: server.getConnectedPlayers()){
			p.sendChat(msg);
		}
	}
}
