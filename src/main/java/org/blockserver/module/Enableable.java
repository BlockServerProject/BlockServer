package org.blockserver.module;

public interface Enableable {
    void onEnable();

    boolean isEnabled();

    void onDisable();
}
