package org.blockserver.core.event;

import java.util.Map;
import java.util.WeakHashMap;

public interface Cancellable {
    Map<Cancellable, Boolean> instances = new WeakHashMap<>();

    default boolean isCancelled() {
        return instances.getOrDefault(this, false);
    }

    default void setCancelled(boolean cancelled) {
        instances.put(this, cancelled);
    }
}