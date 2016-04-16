package org.blockserver.server.core.util;

/**
 * Utility class: represents a task that can be ran at a certain time.
 */
public class Task {
    public Runnable r;
    public long runAt;
}
