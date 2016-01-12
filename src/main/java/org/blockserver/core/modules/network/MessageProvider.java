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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Written by Exerosis!
 */
public class MessageProvider extends NetworkProvider {
    private final BlockingQueue<Message> messageOutQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> messageInQueue = new LinkedBlockingQueue<>();
    private final SchedulerModule schedulerModule;
    private final NetworkConverter converter;
    private final Runnable task;

    public MessageProvider(Server server, SchedulerModule schedulerModule, NetworkConverter converter) {
        super(server);
        this.schedulerModule = schedulerModule;
        this.converter = converter;

        task = () -> {
            for (Message message : messageOutQueue) {
                getServer().getEventManager().fire(new MessageHandleEvent<>(message));
            }
            messageOutQueue.clear();
        };

        new ServerEventListener<RawPacketHandleEvent>() {
            @Override
            public void onEvent(RawPacketHandleEvent event) {
                if (event.isCancelled())
                    return;


                getServer().getExecutorService().execute(() -> {
                    getServer().getEventManager().fire(new MessageHandleEvent<>(converter.toMessage(event.getPacket())));
                });
            }
        }.post().priority(Priority.MONITOR).register(RawPacketHandleEvent.class, getServer());
    }

    @Override
    public void queueInboundPackets(RawPacket... packets) {
        super.queueInboundPackets(packets);
    }

    public void queueInboundMessages(Message... messages) {
        Collections.addAll(messageInQueue, messages);
    }

    public void queueOutboundMessages(Message... messages) {
        Collections.addAll(messageOutQueue, messages);
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
        return new HashSet<>(messageOutQueue);
    }

    public Collection<Message> getMessageOutQueue() {
        return new HashSet<>(messageInQueue);
    }
}