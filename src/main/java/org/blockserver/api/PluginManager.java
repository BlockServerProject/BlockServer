package org.blockserver.api;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents a plugin manager.
 */
public interface PluginManager {

    ArrayList<Plugin> getPlugins();

    void loadPlugin(File location);
    void enablePlugin(Plugin plugin);
    void disablePlugin(Plugin plugin);
}
