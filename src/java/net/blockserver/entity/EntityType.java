package net.blockserver.entity;

import net.blockserver.player.Player;

public enum EntityType {
	CHICKEN(10, Chicken.class),
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
