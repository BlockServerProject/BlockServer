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
package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.message.Message;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.scheduler.SchedulerModule;

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
                entry.getValue().toMessages(entry.getKey().receivePackets()).forEach(m -> getServer().getEventManager().fire(new MessageHandleEvent<>(m)));
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