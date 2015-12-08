package org.blockserver.core.modules.scheduler;

import lombok.Getter;
import lombok.Setter;

/**
 * Written by Exerosis!
 */
public class TaskData {
    @Getter @Setter protected long lastTickTime;
    @Getter @Setter protected double delay;
    @Getter @Setter protected int repeatTimes;
    @Getter @Setter protected boolean async;

    public TaskData(double delay, int repeatTimes, boolean async) {
        this.delay = delay;
        this.repeatTimes = repeatTimes;
        this.async = async;
    }

    public long getNextTickTime(){
        return lastTickTime + (long) delay;
    }
}