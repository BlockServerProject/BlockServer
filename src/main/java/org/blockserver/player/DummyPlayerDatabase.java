package org.blockserver.player;

public class DummyPlayerDatabase extends PlayerDatabase{
	@Override
	public PlayerData readPlayer(Player player){
		validate();
		return dummy(player);
	}
	@Override
	public boolean savePlayer(PlayerData data){
		validate();
		return true;
	}
}
