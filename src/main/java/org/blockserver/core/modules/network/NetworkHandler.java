package org.blockserver.core.modules.network;


import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.ServerModule;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NetworkHandler extends ServerModule {
    @Getter private final Set<Dispatcher> dispatchers = Collections.synchronizedSet(new HashSet<>());

    public NetworkHandler(Server server) {
        super(server);
    }

    public void provide(RawPacket packet) {
        for (Dispatcher dispatcher : dispatchers) {
            dispatcher.dispatch(packet);
        }

    }

    public void unregisterDispatcher(Dispatcher dispatcher) {
        dispatchers.remove(dispatcher);
    }

    public void registerDispatcher(Dispatcher dispatcher) {
        dispatchers.add(dispatcher);
    }
}