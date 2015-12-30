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
package org.blockserver.core.modules.world;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.modules.player.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Written by Exerosis!
 */
public class World {
    @Getter private Server server;
    @Getter private Set<Player> players = new HashSet<>();

    public World(Server server) {
        this.server = server;
    }


}
