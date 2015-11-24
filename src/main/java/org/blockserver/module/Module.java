package org.blockserver.module;

import lombok.Getter;
import org.blockserver.Server;

/**
 * Base class for all modules.
 *
 * @author BlockServer Team
 */
public abstract class Module implements Enableable {
    @Getter private final Server server;
    private boolean enabled;

    public Module(Server server) {
        this.server = server;
    }

    @Override
    public void onEnable() {
        enabled = true;
    }

    @Override
    public void onDisable() {
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}