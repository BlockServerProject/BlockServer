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

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.message.Message;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Written by Exerosis!
 */
//TODO deal with weird ... vs Collection stuff.
public class NetworkModule extends Module {
    private final SchedulerModule scheduler;
    @Getter private final Set<NetworkProvider> providers = Collections.synchronizedSet(Collections.unmodifiableSet(new HashSet<>()));
    @Getter private final Runnable task;

    public NetworkModule(Server server, SchedulerModule scheduler) {
        super(server);
        this.scheduler = scheduler;
        task = () -> {
            for (NetworkProvider provider : providers) {
                provider.receiveInboundMessages().forEach(m -> getServer().getEventManager().fire(new MessageHandleEvent<>(m)));
            }
        };
    }

    public void sendMessages(Message... messages) {
        for (NetworkProvider provider : providers) {
            provider.queueOutboundMessages(messages);
        }
    }

    public void registerProvider(NetworkProvider provider) {
        providers.add(provider);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        scheduler.registerTask(task, 1.0, Integer.MAX_VALUE);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        scheduler.cancelTask(task);
    }
}