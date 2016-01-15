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
package org.blockserver.core.modules.serverlist;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.event.EventListener;
import org.blockserver.core.events.RawPacketHandleEvent;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Written by Exerosis!
 */
public class ServerListModule extends Module {
    private final SchedulerModule schedulerModule;
    private final NetworkModule networkModule;
    @Getter private final Runnable task;

    public ServerListModule(Server server, SchedulerModule schedulerModule, NetworkModule networkModule) {
        super(server);
        this.schedulerModule = schedulerModule;
        this.networkModule = networkModule;
        task = () -> {
            //networkModule.sendPackets();
            //send things
        };
        new EventListener<RawPacketHandleEvent, RawPacketHandleEvent>() {
            @Override
            public void onEvent(RawPacketHandleEvent event) {
                //receive pings
                //send pongs
            }
        }.register(RawPacketHandleEvent.class, getServer().getEventManager());
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
}
