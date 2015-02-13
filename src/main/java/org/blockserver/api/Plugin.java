package org.blockserver.api;

import org.blockserver.Server;
import org.blockserver.ui.Logger;

/**
 * Represents a Plugin.
 */
public interface Plugin {

    void onLoad(Server server, Logger logger);

    void onEnable();
    void onDisable();

    boolean isLoaded();
    boolean isEnabled();

    String getName();
}
