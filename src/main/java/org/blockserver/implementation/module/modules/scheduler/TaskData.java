package org.blockserver.implementation.module.modules.scheduler;

import lombok.Getter;
import lombok.Setter;

/**
 * Written by Exerosis!
 */
public class TaskData {
    @Getter @Setter protected long lastTickTime;
    @Getter @Setter protected double delay;
    @Getter @Setter protected int repeatTimes;

    public TaskData(double delay, int repeatTimes) {
        this.delay = delay;
        this.repeatTimes = repeatTimes;
    }

    public long getNextTickTime(){
        return lastTickTime + (long) delay;
    }
}