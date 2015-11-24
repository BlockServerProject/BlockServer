package org.blockserver.event.system;

public enum Priority {

    LOWEST(0), LOW(1), NORMAL(2), HIGH(3), HIGHEST(4), MONITOR(5);

    private final int slot;

    Priority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }
}