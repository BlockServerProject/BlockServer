/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core.event;

import org.blockserver.core.Server;

/**
 * Written by Exerosis!
 */
public class ServerEventListener<B> extends EventListener<B, B> {
    public void register(Class<B> listenerType, Server server) {
        register(listenerType, server.getEventManager());
    }

    public void unregister(Server server) {
        unregister(server.getEventManager());
    }

    public ServerEventListener<B> post() {
        super.post();
        return this;
    }

    public ServerEventListener<B> priority(Priority priority) {
        super.priority(priority);
        return this;
    }
}