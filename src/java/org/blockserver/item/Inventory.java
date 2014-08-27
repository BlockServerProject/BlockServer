package org.blockserver.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.blockserver.entity.Entity;
import org.blockserver.utility.ArrayIterator;

public class Inventory implements Collection<Item>{
	protected Entity owner;
	protected int capacity;
	protected Item[] items;

	public Inventory(Entity owner, int capacity){
		this.owner = owner;
		items = new Item[capacity];
		this.capacity = capacity;
		clear();
	}

	/**
	 * <b>Warning: This method modifies the count value of the passed item parameter <code>e</code>.
	 * If the modification isn't desired, pass <code>e.clone()</code> instead.</b>
	 * @param <code>e</code> The item to add
	 */
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
			else if(item.getID() == 0){
				items[i] = new Item(e.getID(), e.getDamage(), 0, e.getName(), e.getMaxCount());
				slots.put(i, e.getMaxCount());
				sum += e.getMaxCount();
			}
			i++;
		}
		if(sum == 0){
			return false;
		}
		for(Map.Entry<Integer, Integer> entry: slots.entrySet()){
			if(e.getCount() == 0){
				break;
			}
			int available = entry.getValue();
			if(e.getCount() >= available){
				items[entry.getKey()].setCount(items[entry.getKey()].getMaxCount());
				e.setCount(e.getCount() - available);
			}
			else{
				items[entry.getKey()].setCount(e.getCount() + items[entry.getKey()].getCount());
				e.setCount(0);
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Item> c){
		boolean ret = false;
		for(Item i: c){
			ret = ret || add(i);
		}
		return ret;
	}

	@Override
	public void clear(){
		for(int i = 0; i < capacity; i++){
			items[i] = new Item(0, 0, 0, "Unknown", 0);
		}
	}

	@Override
	public boolean contains(Object o){
		if(o instanceof Item){
			for(Item i: items){
				if(((Item) o).curEquals(i, false)){
					return true;
				}
			}
			return false;
		}
		if(o instanceof ItemType){
			for(Item i: items){
				if(((ItemType) o).getID() == i.getID() && ((ItemType) o).getDamage() == i.getDamage()){
					return true;
				}
			}
			return false;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public boolean containsAll(Collection<?> c){
		boolean ret = true;
		for(Object i: c){
			ret = ret && contains(i);
		}
		return ret;
	}

	@Override
	public boolean isEmpty(){
		for(Item i: items){
			if(i.getID() != 0){
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<Item> iterator(){
		return new ArrayIterator<Item>(items); // not sure if we have that class in Apache libs
	}

	@Override
	public boolean remove(Object o){
		if(!(o instanceof Integer)){
			throw new ClassCastException();
		}
		items[(Integer) o] = new Item(0, 0, 0, "", 0);
		return true;
	}
	public boolean remove(int i){
		return remove((Integer) i);
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
