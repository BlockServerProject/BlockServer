package org.blockserver.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.Entity;
import org.blockserver.objects.IInventory;
import org.blockserver.utility.ArrayIterator;

public class Inventory implements Collection<Item>, IInventory<Item>{
	protected Entity owner;
	protected int capacity;
	protected Item[] items;
	protected Server server;

	public Inventory(Entity owner, int capacity, Server server){
		this.owner = owner;
		this.server = server;
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
	public boolean isEmpty(){
		for(Item i: items){
			if(i.getID() != 0){
				return false;
			}
		}
		return true;
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
	public Iterator<Item> iterator(){
		return new ArrayIterator<Item>(items); // not sure if we have that class in Apache libs
	}

	public Item[] getItems(){
		return items;
	}

	public boolean clearSlot(int i){
		if(items[(Integer) i].getID() == 0){
			return false;
		}
		items[(Integer) i] = new Item(0, 0, 0, "", 0);
		return true;
	}

	public boolean removeItem(Item item){
		return removeItem(item, item.getCount());
	}
	public boolean removeItem(Item item, int count){
		return false;
	}

	public boolean clearSlots(int[] slots){
		boolean result = false;
		for(int slot: slots){
			result = result || clearSlot(slot);
		}
		return result;
	}
	public boolean clearSlots(Collection<Integer> slots){
		boolean result = false;
		for(Integer slot: slots){
			result = result || clearSlot(slot);
		}
		return result;
	}

	public boolean removeItems(Item[] items){
		boolean result = false;
		for(Item item: items){
			result = result || removeItem(item);
		}
		return result;
	}
	public boolean removeItems(Collection<Item> items){
		boolean result = false;
		for(Item item: items){
			result = result || removeItem(item);
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c){
		Object sample = c.iterator().next();
		if(!(sample instanceof Item)){
			if(sample == null){
				clear();
				server.getLogger().warning("Bad call to Inventory.retainAll(Collection): "
						+ "should not pass empty collection to this method. "
						+ "Use Inventory.clear() instead.");
				return true;
			}
			else{
				throw new IllegalArgumentException(String.format("Parameter 1 must be instance of Collection<Item>, Collection<%s> given.", sample.getClass().getSimpleName()));
			}
		}
		List<Integer> slots = new ArrayList<Integer>(capacity);
		int i = 0;
		for(Item item: items){
			for(Item match: (Item[]) c.toArray()){
				if(match.curEquals(item, false)){
					slots.add(i);
					break;
				}
			}
			i++;
		}
		for(i = 0; i < capacity; i++){
			if(!slots.contains(i)){
				items[i] = new Item(0, 0, 0, "", 0);
			}
		}
		return slots.size() > 0;
	}

	@Override
	public int size(){
		return capacity;
	}
	public int getSize(){
		return capacity;
	}

	@Override
	public Object[] toArray(){
		return items;
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T>T[] toArray(T[] a){
		if(!(a instanceof Item[])){
			throw new ClassCastException("Argument passed must be instance of Item[]");
		}
		if(a.length >= capacity){
			int i = 0;
			for(Item item: items){
				a[i] = (T) item;
			}
			return null; // according to the JavaDoc
		}
		return (T[]) items;
	}

	@Override
	public boolean remove(Object o){
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean removeAll(Collection<?> c){
		throw new UnsupportedOperationException();
	}
}
