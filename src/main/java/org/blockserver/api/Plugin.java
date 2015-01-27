package org.blockserver.api;

import org.blockserver.Server;

/**
 * Represents a Plugin.
 */
public interface Plugin {

    void onLoad(Server server);

    void onEnable();
    void onDisable();

    boolean isLoaded();
    boolean isEnabled();

    String getName();
}
