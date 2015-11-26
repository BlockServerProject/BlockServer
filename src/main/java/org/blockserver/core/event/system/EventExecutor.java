package org.blockserver.core.event.system;

public interface EventExecutor<T> {
    void execute(T event);
}