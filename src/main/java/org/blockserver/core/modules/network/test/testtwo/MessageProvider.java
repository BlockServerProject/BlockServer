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
package org.blockserver.core.modules.network.test.testtwo;

import org.blockserver.core.Server;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.events.packets.PacketReceiveEvent;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.network.NetworkConverter;
import org.blockserver.core.modules.network.RawPacket;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Written by Exerosis!
 */
public class MessageProvider extends PacketProvider {
    private final BlockingQueue<Message> messageInQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> messageOutQueue = new LinkedBlockingQueue<>();
    private final NetworkConverter converter;
    private final Runnable task;
    private SchedulerModule schedulerModule;

    public MessageProvider(Server server, NetworkConverter converter, SchedulerModule schedulerModule) {
        super(server);
        this.converter = converter;
        this.schedulerModule = schedulerModule;
        task = () -> {
            for (Message message : messageOutQueue)
                getServer().getEventManager().fire(new MessageHandleEvent<>(message), event -> {
                    if (!event.isCancelled())
                        getServer().getExecutorService().execute(() -> queueOutboundPacket(converter.toPacket(event.getMessage())));
                });

            for (Message message : messageInQueue)
                getServer().getEventManager().fire(new MessageHandleEvent<>(message));
            messageInQueue.clear();
        };
    }

    public void queueOutboundMessage(Message message) {
        if (message.isAsync()) {
            getServer().getEventManager().fire(new MessageHandleEvent<>(message), event -> {
                if (!event.isCancelled())
                    getServer().getExecutorService().execute(() -> queueOutboundPacket(converter.toPacket(event.getMessage())));
            });
        } else
            messageOutQueue.add(message);
    }

    public void queueInboundMessage(Message message) {
        if (message.isAsync())
            getServer().getEventManager().fire(new MessageHandleEvent<>(message));
        else
            messageInQueue.add(message);
    }

    @Override
    public void queueInboundPacket(RawPacket packet) {
        getServer().getEventManager().fire(new PacketReceiveEvent(packet), event -> {
            if (!event.isCancelled())
                getServer().getExecutorService().execute(() -> queueInboundMessage(converter.toMessage(packet)));
        });
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

    public Collection<Message> getMessageInQueue() {
        return new HashSet<>(messageInQueue);
    }
}