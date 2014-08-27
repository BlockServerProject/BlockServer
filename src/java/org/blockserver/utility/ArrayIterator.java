package org.blockserver.utility;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T>{
	protected T[] array;
	protected int curOffset = 0;
	public ArrayIterator(T[] array){
		this.array = array;
	}
	@Override
	public boolean hasNext(){
		return curOffset + 1 < array.length;
	}
	public void remove(){
		throw new UnsupportedOperationException();
	}
	@Override
	public T next(){
		try{
			return array[curOffset++];
		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new NoSuchElementException();
		}
	}
}
