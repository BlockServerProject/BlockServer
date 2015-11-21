package org.blockserver.event;

public interface EventExecutor<T> {
    void execute(T event);
}