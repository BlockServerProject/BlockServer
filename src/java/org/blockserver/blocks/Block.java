package org.blockserver.blocks;

public class Block{
	private byte id;
	private byte damage;
	private String name;

	public Block(int id){
		this("Unknown", id, 0);
	}
	public Block(BlockType block){
		this(block.name(), block.getID(), block.getDamage());
	}
	public Block(String name, int id){
		this(name, id, 0);
	}
	public Block(String name, int id, int damage){
		this.id = (byte) id;
		this.damage = (byte) damage;
		this.name = name.isEmpty() ? "Unknown":name;
	}

	public byte getID(){
		return id;
	}
	public byte getDamage(){
		return damage;
	}
	public String getName(){
		return name;
	}
}
