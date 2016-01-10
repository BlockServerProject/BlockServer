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
import org.blockserver.core.events.RawPacketHandleEvent;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Written by Exerosis!
 */
public class NetworkModule extends Module {
    private final SchedulerModule schedulerModule;
    private final Set<NetworkProvider> providers = Collections.synchronizedSet(new HashSet<>());
    @Getter protected Runnable task;

    public NetworkModule(Server server, SchedulerModule schedulerModule) {
        super(server);
        this.schedulerModule = schedulerModule;
        task = () -> {
            for (NetworkProvider provider : providers) {
                provider.receiveInboundPackets().forEach(p -> getServer().getEventManager().fire(new RawPacketHandleEvent(p)));
            }
        };
    }

    public void broadcastPackets(RawPacket... packets) {
        for (NetworkProvider provider : providers) {
            provider.queueOutboundPackets(packets);
        }
    }

    public void sendPackets(NetworkProvider provider, RawPacket... packets) {
        provider.queueOutboundPackets(packets);
    }

    public void registerProvider(NetworkProvider provider) {
        providers.add(provider);
    }

    @Override
    public void onEnable() {
        schedulerModule.registerTask(task, 1.0, Integer.MAX_VALUE);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        schedulerModule.cancelTask(task);
        super.onDisable();
    }

    public Set<NetworkProvider> getProviders() {
        return Collections.unmodifiableSet(providers);
    }
}