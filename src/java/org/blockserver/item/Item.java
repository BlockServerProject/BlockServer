package org.blockserver.item;

import java.util.HashMap;
import java.util.Map;

import org.blockserver.objects.IItem;

public class Item implements IItem{
	private short id;
	private byte damage;
	private byte count;
	private String name;
	private int maxCount;
	private Map<CharSequence, String> metadata;

	public Item(int id, int damage, int count, String name, int maxCount){
		this(id, damage, count, name, maxCount, new HashMap<CharSequence, String>(0));
	}
	public Item(int id, int damage, int count, String name, int maxCount, Map<CharSequence, String> metadata){
		this.id = (short) id;
		this.damage = (byte) damage;
		this.count = (byte) count;
		this.name = name;
		this.maxCount = maxCount;
		this.metadata = metadata;
	}
	public Item(ItemType type, int count){
		this(type, count, new HashMap<CharSequence, String>(0));
	}
	public Item(ItemType type, int count, Map<CharSequence, String> metadata){
		this(type.getID(), type.getDamage() == ItemType.DAMAGE_NEGLIGIBLE ? 0:type.getDamage(),
				count, type.getName(), type.getMaxCount(), metadata);
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
	public void setDamage(int newDamage){
		damage = (byte) newDamage;
	}
	public void setCount(int newCount){
		count = (byte) newCount;
	}
	public String getName(){
		return name;
	}
	public int getMaxCount(){
		return maxCount;
	}

	public Map<CharSequence, String> getMetadata(){
		return metadata;
	}
	public String getMetadata(String name){
		return getMetadata(name, null);
	}
	public String getMetadata(CharSequence name, String defaultValue){
		String result = metadata.get(name);
		if(result == null){
			result = defaultValue;
		}
		return result;
	}
	public void setMetadata(Map<CharSequence, String> map){
		metadata = map;
	}
	public void setMetadata(CharSequence name, String metadata){
		this.metadata.put(name, metadata);
	}

	public boolean canContainMore(){
		return maxCount - count > 0;
	}
	public int canContainHowManyMore(){
		return Math.max(0, maxCount - count);
	}
}
