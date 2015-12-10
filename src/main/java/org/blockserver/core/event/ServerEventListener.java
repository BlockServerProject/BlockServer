package org.blockserver.core.event;

import org.blockserver.core.Server;

/**
 * Written by Exerosis!
 */
public class ServerEventListener<T> extends EventListener<T, T> {
    public ServerEventListener<T> register(Class<T> listenerType, Server server) {
        return (ServerEventListener<T>) register(listenerType, server.getEventManager());
    }
}
