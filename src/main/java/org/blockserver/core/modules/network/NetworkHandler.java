package org.blockserver.core.modules.network;


import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NetworkHandler extends Module {
    @Getter private final Set<Provider> providers = Collections.synchronizedSet(new HashSet<>());
    @Getter private final Set<Dispatcher> dispatchers = Collections.synchronizedSet(new HashSet<>());

    public NetworkHandler(Server server) {
        super(server);
        getServer().getExecutorService().execute(() -> {
            while (isEnabled()) {
                providers.stream().filter(p -> p.peek() != null).forEach(p -> {
                    for (Dispatcher dispatcher : dispatchers)
                        dispatcher.dispatch(p.poll());
                });
            }
        });
    }

    public void unregisterProvider(Provider provider) {
        providers.remove(provider);
    }

    public void unregisterDispatcher(Dispatcher dispatcher) {
        dispatchers.remove(dispatcher);
    }

    public void registerProvider(Provider provider) {
        providers.add(provider);
    }

    public void registerDispatcher(Dispatcher dispatcher) {
        dispatchers.add(dispatcher);
    }
}