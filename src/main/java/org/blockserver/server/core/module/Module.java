package org.blockserver.server.core.module;

import lombok.Getter;
import org.blockserver.server.core.Server;

/**
 * Represents an extension that adds features
 * to the Server
 *
 * @author BlockServer Team
 */
public abstract class Module {
    @Getter private Server server;

    public final void init(Server server) {
        this.server = server;
    }

    public abstract void register();

    public void cleanup() {

    }
}
