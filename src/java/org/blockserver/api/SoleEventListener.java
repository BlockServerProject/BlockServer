package org.blockserver.api;

import org.blockserver.player.Player;
import org.blockserver.utility.Argument;

public interface SoleEventListener{
	public void onConnect(Player player, Argument<Boolean> allow);
	public void onJoin(Player player, Argument<String> joinMessage);
	public void onQuit(Player player, Argument<String> quitMessage);

	public static class DummySoleEventListener implements SoleEventListener{
		@Override public void onConnect(Player player, Argument<Boolean> allow){}
		@Override public void onJoin(Player player, Argument<String> joinMessage){}
		@Override public void onQuit(Player player, Argument<String> quitMessage){}
	}
}
