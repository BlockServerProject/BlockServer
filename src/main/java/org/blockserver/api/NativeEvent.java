package org.blockserver.api;

/**
 * Base class for all events, Module events must extend this too.
 */
public abstract class NativeEvent{
	private boolean cancelled = false;

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public boolean isCancellable(){
		return true;
	}
}
