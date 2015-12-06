package org.blockserver.core;

import lombok.Getter;
import org.blockserver.core.modules.network.message.Message;

import java.net.SocketAddress;

/**
 * Represents a Player on the server.
 *
 * @author BlockServer Team
 */
public class Player {
    @Getter private Server server;
    @Getter private SocketAddress address;

    public Player(Server server, SocketAddress address) {
        this.server = server;
        this.address = address;
    }

    public void handleMessage(Message message) {

    }
}
