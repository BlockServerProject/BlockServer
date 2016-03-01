package org.blockserver.core.module;

/**
 * Represents something that can be enabled and disabled.
 */
public interface Enableable {
    void enable();

    boolean isEnabled();

    void disable();
}
