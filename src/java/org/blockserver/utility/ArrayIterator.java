package org.blockserver.utility;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T>{
	protected T[] array;
//	protected boolean hasRemoved = false;
	protected int curOffset = 0;

	public ArrayIterator(T[] array){
		this.array = array;
	}

	@Override
	public boolean hasNext(){
		return curOffset + 1 < array.length;
	}
	public void remove(){
		throw new UnsupportedOperationException(); // Apache library does this
		// actual implementation
//		if(hasRemoved){
//			throw new IllegalStateException();
//		}
//		if(curOffset > 0){
//			array[curOffset - 1] = null;
//			hasRemoved = true;
//		}
//		else{
//			throw new IllegalStateException();
//		}
	}
	@Override
	public T next(){
		try{
//			hasRemoved = false;
			return array[curOffset++];
		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new NoSuchElementException();
		}
	}
}
