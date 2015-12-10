package org.blockserver.core.event;

/**
 * Written by Exerosis!
 */
public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
