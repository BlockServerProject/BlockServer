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
package org.blockserver.core.modules.message;

import org.blockserver.core.Server;
import org.blockserver.core.event.Priority;
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.events.messages.MessageReceiveEvent;
import org.blockserver.core.events.messages.MessageSendEvent;
import org.blockserver.core.modules.network.NetworkConverter;
import org.blockserver.core.modules.network.pipeline.NetworkPipelineHandler;
import org.blockserver.core.modules.network.pipeline.PipelineDispatcher;
import org.blockserver.core.modules.network.pipeline.PipelineProviderImplementation;
import org.blockserver.core.modules.network.pipeline.packet.RawPacket;
import org.blockserver.core.modules.thread.ExecutorModule;

public class MessageModule extends PipelineProviderImplementation implements PipelineDispatcher {
    private final ServerEventListener<MessageSendEvent> listener;
    private final ExecutorModule executorModule;
    private final NetworkConverter converter;

    public MessageModule(Server server, ExecutorModule executorModule, NetworkPipelineHandler handler, NetworkConverter converter) {
        super(server, handler);
        this.executorModule = executorModule;
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
    public void enable() {
        listener.register(MessageSendEvent.class, getServer());
        super.enable();
    }

    @Override
    public void disable() {
        listener.unregister(getServer());
        super.disable();
    }

    @Override
    public void dispatch(RawPacket packet) {
        executorModule.getExecutorService().execute(() -> getServer().getEventManager().fire(new MessageReceiveEvent<>(converter.toMessage(packet))));
    }
}