/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
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

		public DummyAPI(Server server){
			super(server);
		}

		@Override
		public boolean handleEvent(NativeEvent evt){
			return !evt.isCancelled();
		}
	}
}
