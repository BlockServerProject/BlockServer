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
import org.blockserver.core.message.Message;
import org.blockserver.core.modules.logging.LoggingModule;
import org.blockserver.core.modules.network.NetworkConverter;
import org.blockserver.core.modules.network.NetworkModule;
import org.blockserver.core.modules.network.NetworkProvider;

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
    @Getter private final String name;
    @Getter private final UUID UUID;
    @Getter private final NetworkProvider provider;

    public Player(Server server, InetSocketAddress address, String name, UUID UUID, NetworkProvider provider) {
        this.server = server;
        this.address = address;
        this.name = name;
        this.UUID = UUID;
        this.provider = provider;
    }

    public void sendMessage(Message message) {
        server.getModule(NetworkModule.class).sendPackets(provider.getConverter().toPacket(message));
    }

    public void handleMessage(Message message) {
        server.getModule(LoggingModule.class).debug("Got Message: "+message);
    }
}