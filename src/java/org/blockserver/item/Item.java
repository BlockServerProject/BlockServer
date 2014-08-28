package org.blockserver.item;

public class Item{
	private int id;
	private int damage;
	private int count;
	private String name;
	private int maxCount;

	public Item(int id, int damage, int count, String name, int maxCount){
		this.id = id;
		this.damage = damage;
		this.count = count;
		this.name = name;
		this.maxCount = maxCount;
	}

	public Item(ItemType type, int count){
		this(type.getID(), type.getDamage() == ItemType.DAMAGE_NEGLIGIBLE ? 0:type.getDamage(),
				count, type.getName(), type.getMaxCount());
	}

	public boolean curEquals(Item other, boolean checkCount){
		return other.getID() == id && other.getDamage() == damage && (checkCount ? other.getCount() == count:true);
	}

	public int getID(){
		return id;
	}
	public int getDamage(){
		return damage;
	}
	public int getCount(){
		return count;
	}
	public void setCount(int newCount){
		count = newCount;
	}
	public String getName(){
		return name;
	}
	public int getMaxCount(){
		return maxCount;
	}
	public boolean canContainMore(){
		return maxCount - count > 0;
	}
	public int canContainHowManyMore(){
		return Math.max(0, maxCount - count);
	}
}
