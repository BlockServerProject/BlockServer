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
package org.blockserver.core.modules.network.test;

import org.blockserver.core.Server;
import org.blockserver.core.event.Priority;
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.events.packets.PacketEvent;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.network.NetworkConverter;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Written by Exerosis!
 */
public class MessageProvider extends Module {
    private final BlockingQueue<Message> syncQueue = new LinkedBlockingQueue<>();
    private final NetworkConverter converter;
    private final SchedulerModule schedulerModule;
    private Runnable task;

    public MessageProvider(Server server, NetworkConverter converter, SchedulerModule schedulerModule) {
        super(server);
        this.converter = converter;
        this.schedulerModule = schedulerModule;

        new ServerEventListener<PacketEvent>() {
            @Override
            public void onEvent(PacketEvent event) {
                getServer().getExecutorService().execute(() -> fireMessageEvent(converter.toMessage(event.getPacket())));
            }
        }.priority(Priority.INTERNAL).post().register(PacketEvent.class, getServer());

        task = () -> {
            for (Message message : syncQueue)
                getServer().getEventManager().fire(new MessageHandleEvent<>(message));
            syncQueue.clear();
        };
    }

    private void fireMessageEvent(Message message) {
        if (message.isAsync())
            getServer().getEventManager().fire(new MessageHandleEvent<>(message));
        else
            syncQueue.add(message);
    }

    public void queueOutboundMessage(Message message) {

    }

    public void queueInboundMessage(Message message) {

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
