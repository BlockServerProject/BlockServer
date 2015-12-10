package org.blockserver.core.module;

/**
 * Written by Exerosis!
 */
public interface Enableable {
    void onEnable();
    boolean isEnabled();
    void onDisable();
}
