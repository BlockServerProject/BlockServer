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
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.events.packets.PacketEvent;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Written by Exerosis!
 *
 * @author BlockServer Team
 * @see org.blockserver.core.module.Module
 */
public class ServerListModule extends Module {
    private final SchedulerModule schedulerModule;
    @Getter private final Runnable task;
    private final ServerEventListener<PacketEvent> listener;

    public ServerListModule(Server server, SchedulerModule schedulerModule) {
        super(server);
        this.schedulerModule = schedulerModule;
        task = () -> {
            //networkModule.sendPackets();
            //send things
        };
        listener = new ServerEventListener<PacketEvent>() {
            @Override
            public void onEvent(PacketEvent event) {
                //receive pings
                //send pongs
            }
        };
    }

    @Override
    public void onEnable() {
        schedulerModule.registerTask(task, 1.0, Integer.MAX_VALUE);
        listener.register(PacketEvent.class, getServer());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        schedulerModule.cancelTask(task);
        listener.unregister(getServer());
        super.onDisable();
    }
}
