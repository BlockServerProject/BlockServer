package org.blockserver.module;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.blockserver.Server;

/**
 * Base class for all modules.
 *
 * @author BlockServer Team
 */
public abstract class Module {
    @Getter private final Server server;
    @Getter @Setter(AccessLevel.PROTECTED) private String name = "unknown";
    private boolean enabled;

    public Module(Server server) {
        this.server = server;
    }

    /**
     * Handles a message
     */
    public void handleMessage() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

}
