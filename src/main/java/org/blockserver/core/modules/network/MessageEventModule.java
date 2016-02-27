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
import org.blockserver.core.events.messages.MessageReceiveEvent;
import org.blockserver.core.events.messages.MessageSendEvent;

public class MessageEventModule extends NetworkProvider implements NetworkDispatcher {
    private final ServerEventListener<MessageSendEvent> listener;
    private final NetworkConverter converter;

    public MessageEventModule(Server server, NetworkPipelineHandler handler, NetworkConverter converter) {
        super(handler, server);
        this.converter = converter;
        listener = new ServerEventListener<MessageSendEvent>() {
            @Override
            public void onEvent(MessageSendEvent event) {
                if (!event.isCancelled())
                    provide(converter.toPacket(event.getMessage()));
            }
        }.priority(Priority.INTERNAL).post();
    }

    @Override
    public void onEnable() {
        listener.register(MessageSendEvent.class, getServer());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        listener.unregister(getServer());
        super.onDisable();
    }

    @Override
    public void dispatch(RawPacket packet) {
        getServer().getExecutorService().execute(() -> getServer().getEventManager().fire(new MessageReceiveEvent<>(converter.toMessage(packet))));
    }
}