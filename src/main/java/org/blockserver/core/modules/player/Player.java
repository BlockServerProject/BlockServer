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
package org.blockserver.core.modules.player;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.event.MessageEventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.message.PlayerLoginMessage;
import org.blockserver.core.modules.message.block.MessageOutBlockChange;
import org.blockserver.core.modules.network.NetworkModule;
import org.blockserver.core.modules.network.NetworkProvider;
import org.blockserver.core.modules.world.positions.Location;
import org.blockserver.core.utilities.Skin;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Represents a Player on the server.
 *
 * @author BlockServer Team
 */
public class Player {
    @Getter private final Server server;
    @Getter private final InetSocketAddress address;
    @Getter private final NetworkProvider provider;

    @Getter private final String name;
    @Getter private final UUID UUID;
    @Getter private Skin skin;

    public Player(Server server, InetSocketAddress address, String name, UUID UUID, NetworkProvider provider) {
        this.server = server;
        this.address = address;
        this.name = name;
        this.UUID = UUID;
        this.provider = provider;

        new MessageEventListener<PlayerLoginMessage>(){
            @Override
            public void onEvent(MessageHandleEvent<PlayerLoginMessage> event) {
                handleLogin(event.getMessage());
            }
        }.register(PlayerLoginMessage.class, server);
    }

    public Location getLocation() {
        return new Location(0, 64, 0);
    }

    public void sendMessage(Message message) {
        server.getModule(NetworkModule.class).sendPackets(provider, provider.getConverter().toPacket(message));
    }

    public void handleLogin(PlayerLoginMessage message) {
        if(server.getModule(PlayerModule.class).getPlayer(message.username) != null) {
            disconnect("blockserver.player.disconnect.username", true);
        }
        if(server.getModule(PlayerModule.class).getPlayer(message.uuid) != null) {
            disconnect("blockserver.player.disconnect.uuid", true);
        }
    }

    public void disconnect(String message, boolean notify) {

    }
}