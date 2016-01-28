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
import org.blockserver.core.events.messages.MessageEvent;
import org.blockserver.core.modules.message.Message;

/**
 * Written by Exerosis!
 */
public class MessageEventListener<T extends Message> extends EventListener<T, MessageEvent<T>> {
    public MessageEventListener<T> register(Class<T> listenerType, Server server) {
        return (MessageEventListener<T>) register(listenerType, server.getEventManager());
    }

    public void unregister(Server server) {
        unregister(server.getEventManager());
    }
}