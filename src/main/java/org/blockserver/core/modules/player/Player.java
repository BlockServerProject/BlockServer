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
import lombok.Setter;
import org.blockserver.core.Server;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.message.block.MessageOutBlockChange;
import org.blockserver.core.modules.world.positions.Location;

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
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private int z;

    public Player(Server server, InetSocketAddress address, String name, UUID UUID) {
        this.server = server;
        this.address = address;
        this.name = name;
        this.UUID = UUID;
    }

    public Location getLocation() {
        return new Location(x, y, z);
    }

    public void sendMessage(Message message) {

    }
}