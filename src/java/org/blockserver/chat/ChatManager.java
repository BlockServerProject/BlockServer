package org.blockserver.chat;

import org.blockserver.GeneralConstants;
import org.blockserver.Server;
import org.blockserver.player.Player;

public abstract class ChatManager implements GeneralConstants{
	public abstract Server getServer();
	public void handleChat(Player player, String msg){
		validate();
		handlePlayerChat(player, msg);
	}
	protected abstract void handlePlayerChat(Player player, String msg);
	public final void broadcast(String string){
		validate();
		broadcastMsg(string);
	}
	protected abstract void broadcastMsg(String string);
	public final void broadcastMessage(Object... args){
		validate();
		broadcastMsgWithArgs(args);
	}
	/**
	 * @param args - varies with subclasses
	 * @throws UnsupportedOperationException if the subclass doesn't support this method
	 * @throws IllegalArgumentException if the arguments are invalid
	 */
	protected void broadcastMsgWithArgs(Object[] args)
			throws UnsupportedOperationException, IllegalArgumentException{
		throw new UnsupportedOperationException("Method not supported by " + getClass().getSimpleName());
	}
	public void validate(){
		if(getServer() == null){
			throw new IllegalStateException("The ChatManager has not been initialized.");
		}
	}
	public abstract void initialize(Server server);
}
