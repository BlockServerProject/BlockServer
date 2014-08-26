package org.blockserver.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.blockserver.entity.Entity;

public class Inventory implements Collection<Item>{
	protected Entity owner;
	protected int capacity;
	protected Item[] items;

	public Inventory(Entity owner, int capacity){
		this.owner = owner;
		items = new Item[capacity];
	}

	@Override
	public boolean add(Item e){
		Map<Integer, Integer> slots = new HashMap<Integer, Integer>(capacity);
		int sum = 0;
		int i = 0;
		for(Item item: items){
			if(item.curEquals(e, false)){
				if(item.canContainMore()){
					slots.put(i, item.canContainHowManyMore());
					sum += item.canContainHowManyMore();
				}
			}
			i++;
		}
		// TODO
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Item> c){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty(){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Item> iterator(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T>T[] toArray(T[] a){
		// TODO Auto-generated method stub
		return null;
	}
}
