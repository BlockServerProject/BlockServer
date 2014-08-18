package net.blockserver.entity;

public enum EntityType {
	CHICKEN(10, Chicken.class); // TODO more entities
	private int id;
	private Class<?> c;
	private EntityType(int id, Class<?> c){
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
