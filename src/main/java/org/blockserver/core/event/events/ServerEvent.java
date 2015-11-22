package org.blockserver.core.event.events;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.Server;

public class ServerEvent {
    @Getter @Setter private Server server;

    public ServerEvent(Server server) {
        this.server = server;
    }
}