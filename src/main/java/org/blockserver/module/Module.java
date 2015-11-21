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
public abstract class Module implements Enableable {
    @Getter
    private final Server server;
    @Getter @Setter(AccessLevel.PROTECTED)
    private String name = "unknown";
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
}