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
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.events.RawPacketHandleEvent;
import org.blockserver.core.modules.network.NetworkConverter;
import org.blockserver.core.modules.network.NetworkModule;
import org.blockserver.core.modules.network.NetworkProvider;
import org.blockserver.core.modules.player.Player;
import org.blockserver.core.modules.player.PlayerModule;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Written by Exerosis!
 */
public class MessageModule extends NetworkModule {

    //TODO figure out a way to make the converter part async.
    public MessageModule(Server server, SchedulerModule schedulerModule) {
        super(server, schedulerModule);
        task = () -> {
            for (NetworkProvider provider : getProviders()) {
                provider.receiveInboundPackets().forEach(packet -> {
                    getServer().getEventManager().fire(new RawPacketHandleEvent(packet), event -> {
                        if (!event.isCancelled()) {
                            Player player = getServer().getModule(PlayerModule.class).getPlayer(event.getPacket().getAddress());
                            if(player != null) {
                                getServer().getEventManager().fire(new MessageHandleEvent<>(provider.getConverter().toMessage(event.getPacket(), player)));
                            }
                        }
                    });
                });
            }
        };
    }


    /*
    public void sendMessage(Message message) {
        getServer().getExecutorService().execute(() -> {
            for (NetworkProvider provider : getProviders()) {
                provider.queueOutboundPackets(networkConverter.toPacket(message));
            }
        });
    }
    */
}
