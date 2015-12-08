package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Written by Exerosis!
 */
public class NetworkModule extends Module {
    private final SchedulerModule scheduler;
    private final NetworkConverter converter;
    private final NetworkProvider[] providers;
    private Runnable task;

    public NetworkModule(Server server, SchedulerModule scheduler, NetworkConverter converter, NetworkProvider... providers) {
        super(server);
        this.scheduler = scheduler;
        this.converter = converter;
        this.providers = providers;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        task = () -> {
            for (NetworkProvider provider : providers) {
                converter.toMessage(provider.receivePackets()).forEach(m -> getServer().getEventManager().fire(m));
            }
        };
        scheduler.registerTask(task, 1.0, Integer.MAX_VALUE);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        scheduler.cancelTask(task);
    }


}
