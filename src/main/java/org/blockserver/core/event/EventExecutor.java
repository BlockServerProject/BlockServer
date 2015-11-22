package org.blockserver.core.event;

public interface EventExecutor<T> {
    void execute(T event);
}