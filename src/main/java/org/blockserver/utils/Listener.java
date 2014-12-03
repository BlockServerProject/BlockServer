package org.blockserver.utils;

public interface Listener<P>{
	public static class EmptyListener<P> implements Listener<P>{
		public void listen(P param){}
	}
	public void listen(P param);
}
