package org.blockserver.blocks;


public class Block
{
	private int id;
	private int damage;
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
		this.id = id;
		this.damage = damage;
		this.name = name.isEmpty() ? "Unknown":name;
	}

	public int getID(){
		return id;
	}
	public int getDamage(){
		return damage;
	}
	public String getName(){
		return name;
	}

}
