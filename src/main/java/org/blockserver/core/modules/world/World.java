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
