package org.blockserver.core.modules.network.v2;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.network.message.Message;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Written by Exerosis!
 */
public class NetworkModule extends Module {
    private final SchedulerModule scheduler;
    private final Map<NetworkConverter, NetworkProvider> converterMap = new ConcurrentHashMap<>();
    private Runnable task;

    public NetworkModule(Server server, SchedulerModule scheduler) {
        super(server);
        this.scheduler = scheduler;
    }

    public void sendMessage(SocketAddress socketAddress, Message... messages){
        for (NetworkConverter converter : converterMap.keySet())
            converterMap.get(converter).sendPackets(converter.toPacket(Arrays.asList(messages)));
    }

    public void registerProvider(NetworkProvider provider, NetworkConverter converter) {
        if(converterMap.containsKey(provider)) {
            throw new IllegalArgumentException("Provider already registered!");
        }
        converterMap.put(converter, provider);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        task = () -> {
            for (NetworkConverter converter : converterMap.keySet())
                converter.toMessage(converterMap.get(converter).receivePackets()).forEach(m -> getServer().getEventManager().fire(m));
        };
        scheduler.registerTask(task, 1.0, Integer.MAX_VALUE);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        scheduler.cancelTask(task);
    }
}
