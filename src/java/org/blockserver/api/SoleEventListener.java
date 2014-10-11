package org.blockserver.api;

import java.util.List;
import org.blockserver.player.Player;

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
