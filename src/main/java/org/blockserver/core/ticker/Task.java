package org.blockserver.core.ticker;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a task called by the ticker.
 */
public abstract class Task implements Runnable{
    @Getter @Setter(AccessLevel.PROTECTED) private long lastTickRan;
    @Getter @Setter(AccessLevel.PROTECTED) private int delay;
}
