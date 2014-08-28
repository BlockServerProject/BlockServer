package org.blockserver.entity;

import org.blockserver.player.Player;

public enum EntityType {
	PLAYER(63, Player.class); // TODO more entities
	private int id;
	private Class<? extends Entity> c;
	private EntityType(int id, Class<? extends Entity> c){
		this.id = id;
		this.c = c;
	}

	public int getID() {
		return id;
	}
	public Class<?> getEntityClass(){
		return c;
	}
}
