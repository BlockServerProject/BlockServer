package org.blockserver.event.system;

public interface EventExecutor<T> {
    void execute(T event);
}