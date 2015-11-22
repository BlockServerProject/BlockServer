package org.blockserver.core.module;

public interface Enableable {
    void onEnable();

    boolean isEnabled();

    void onDisable();
}
