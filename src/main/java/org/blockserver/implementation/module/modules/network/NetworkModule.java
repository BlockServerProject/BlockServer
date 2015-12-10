package org.blockserver.implementation.module.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.implementation.module.modules.network.message.Message;
import org.blockserver.implementation.module.modules.scheduler.SchedulerModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Written by Exerosis!
 */
public class NetworkModule extends Module {
    private final SchedulerModule scheduler;
    private final Map<NetworkProvider, NetworkConverter> providers = new ConcurrentHashMap<>();
    private Runnable task;

    public NetworkModule(Server server, SchedulerModule scheduler) {
        super(server);
        this.scheduler = scheduler;
    }

    //TODO Maybe work out a better way to send packets, to speed things up a bit. Check if when compiled the for loops switch the fastest config.
    public void sendMessages(Message... messages) {
        for (Map.Entry<NetworkProvider, NetworkConverter> entry : providers.entrySet()) {
            for (Message message : messages) {
                entry.getKey().sendPacket(entry.getValue().toPacket(message));
            }
        }
    }

    public void registerProvider(NetworkProvider provider, NetworkConverter converter) {
        providers.put(provider, converter);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        task = () -> {
            for (Map.Entry<NetworkProvider, NetworkConverter> entry : providers.entrySet()) {
                entry.getValue().toMessages(entry.getKey().receivePackets()).forEach(m -> getServer().getEventManager().fire(m));
            }
        };
        scheduler.registerTask(task, 1.0, Integer.MAX_VALUE);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        scheduler.cancelTask(task);
    }

    public Map<NetworkProvider, NetworkConverter> getProviders() {
        return new HashMap<>(providers);
    }
}