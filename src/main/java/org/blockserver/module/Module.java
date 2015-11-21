package org.blockserver.module;

import lombok.Getter;
import org.blockserver.Server;

/**
 * Base class for all modules.
 *
 * @author BlockServer Team
 */
public abstract class Module {
    @Getter private final Server server;

    public Module(Server server) {
        this.server = server;
    }

    /**
     * Handles a message
     */
    public void handleMessage() {

    }

}
