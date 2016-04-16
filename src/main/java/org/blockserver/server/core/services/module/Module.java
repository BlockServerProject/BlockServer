package org.blockserver.server.core.services.module;

import lombok.Getter;
import org.blockserver.server.core.Server;

/**
 * Represents an external extension that adds features
 * to the Server
 *
 * @author BlockServer Team
 */
public abstract class Module {
    @Getter private Server server;

    public final void init(Server server) {
        this.server = server;
        onLoad();
    }

    protected void onLoad() {

    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }
}
