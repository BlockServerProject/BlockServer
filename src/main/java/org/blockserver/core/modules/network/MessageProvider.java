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
import org.blockserver.core.event.Priority;
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.events.RawPacketHandleEvent;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Written by Exerosis!
 */
public class MessageProvider extends NetworkProvider {
    private final NetworkConverter converter;

    public MessageProvider(Server server, SchedulerModule scheduler, NetworkConverter converter) {
        super(server);
        this.converter = converter;
        new ServerEventListener<RawPacketHandleEvent>() {
            @Override
            public void onEvent(RawPacketHandleEvent event) {
                if (!event.isCancelled())
                    getServer().getExecutorService().execute(() -> {
                        getServer().getEventManager().fire(new MessageHandleEvent<>(converter.toMessage(event.getPacket())));
                    });
            }
        }.post().priority(Priority.MONITOR).register(RawPacketHandleEvent.class, getServer());
    }

    public void queueInboundMessages(Message... messages) {
        getServer().getExecutorService().execute(() -> {
            for (Message message : messages) {
                queueInboundPackets(converter.toPacket(message));
            }
        });
    }

    public void queueOutboundMessages(Message... messages) {
        getServer().getExecutorService().execute(() -> {
            for (Message message : messages) {
                queueOutboundPackets(converter.toPacket(message));
            }
        });
    }
}