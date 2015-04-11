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

    protected abstract boolean handleEvent(Event evt);

    /**
     * Fire an event.
     * @param evt The Event to fire.
     * @return The outcome of the event.
     */
    public boolean fireEvent(Event evt){
        return handleEvent(evt);
    }

    public Server getServer(){
        return server;
    }

	public static class Argument<T>{
		public T value;
		public Argument(T value){
			this.value = value;
		}
	}

    public static class DummyAPI extends API{

        public DummyAPI(Server server) {
            super(server);
        }

        @Override
        protected boolean handleEvent(Event evt) {
            return !evt.isCanceled();
        }
    }
}
