package org.blockserver.api;

import org.blockserver.Server;

/**
 * Represents an API. Must be implemented by ALL api extensions.
 * Return true for boolean functions unless you wanna refuse the event
 */
public abstract class API{
	private Server server;

	/**
	 * Construct a new API.
	 * @param server The server this API belongs to.
	 */
	public API(Server server){
		this.server = server;
	}

	/**
	 * Fire an event.
	 * @param evt The Event to fire.
	 * @return {@code true} if the event should continue, {@code false} otherwise.
	 */
	public abstract boolean handleEvent(NativeEvent evt);

	public Server getServer(){
		return server;
	}

	public static class DummyAPI extends API{

		public DummyAPI(Server server) {
			super(server);
		}

		@Override
		public boolean handleEvent(NativeEvent evt) {
			return !evt.isCancelled();
		}
	}
}
