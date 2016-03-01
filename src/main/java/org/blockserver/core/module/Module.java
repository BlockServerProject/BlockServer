package org.blockserver.core.module;

import lombok.Getter;
import org.blockserver.core.Server;

/**
 * Represents a unique part or section of the server, that can be enabled and
 * disabled.
 *
 * TODO: Module info
 */
public class Module extends EnableableImplementation {
    @Getter private Server server;

    public Module(Server server) {
        this.server = server;
    }
}
