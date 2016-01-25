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
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Created by atzei on 1/25/2016.
 */
public class NetworkModule extends Module{
    private SchedulerModule scheduler;

    public NetworkModule(Server server, SchedulerModule scheduler) {
        super(server);
        this.scheduler = scheduler;
    }

    public void sendPackets(NetworkProvider provider, RawPacket... packets) {

    }

    public void sendMessages(NetworkProvider provider, Message... messages) {

    }
}
