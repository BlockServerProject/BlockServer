package net.blockserver.entity;

import net.blockserver.player.Player;

public enum EntityType {
	CHICKEN(10, Chicken.class),
	COW(11, Cow.class),
	PIG(12, Pig.class),
	SHEEP(13, Sheep.class),
	WOLF(14, Wolf.class),
	VILLAGER(15, Villager.class),
	MOOSHROOM(16, Mooshroom.class),
	ZOMBIE(32, Zombie.class),
	CREEPER(33, Creeper.class),
	SKELETON(34, Skeleton.class),
	SPIDER(35, Spider.class),
	ZOMBIE_PIG(36, ZombiePig.class),
	SLIME(37, Slime.class),
	ENDERMAN(38, Enderman.class),
	SILVERFISH(39, Silverfish.class),
	PLAYER(63, Player.class); // TODO more entities
	/*FALLING_BLOCK(66, FallingBlock.class); I'll implement this later, due to I have to fully
	implement first a block appearing when completely when the entity falls completely. */
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
