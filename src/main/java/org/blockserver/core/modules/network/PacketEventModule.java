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
import org.blockserver.core.events.packets.PacketReceiveEvent;
import org.blockserver.core.events.packets.PacketSendEvent;
import org.blockserver.core.modules.network.pipeline.NetworkPipelineHandler;
import org.blockserver.core.modules.network.pipeline.PipelineDispatcher;
import org.blockserver.core.modules.network.pipeline.PipelineProviderImplementation;
import org.blockserver.core.modules.network.pipeline.packet.RawPacket;

public class PacketEventModule extends PipelineProviderImplementation implements PipelineDispatcher {
    private final ServerEventListener<PacketSendEvent> listener;

    public PacketEventModule(NetworkPipelineHandler handler, Server server) {
        super(server, handler);
        listener = new ServerEventListener<PacketSendEvent>() {
            @Override
            public void onEvent(PacketSendEvent event) {
                if (!event.isCancelled())
                    provide(event.getPacket());
            }
        }.priority(Priority.INTERNAL).post();
    }

    @Override
    public void enable() {
        listener.register(PacketSendEvent.class, getServer());
        super.enable();
    }

    @Override
    public void disable() {
        listener.unregister(getServer());
        super.disable();
    }

    @Override
    public void dispatch(RawPacket packet) {
        getServer().getEventManager().fire(new PacketReceiveEvent(packet));
    }
}