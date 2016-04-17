package org.blockserver.server.core;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.server.core.module.ModuleLoader;
import org.blockserver.server.core.util.Task;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * The main server implementation.
 *
 * @author BlockServer Team
 */
public class Server implements Runnable {
    public static final String SOFTWARE = "BlockServer";
    public static final String SOFTWARE_VERSION = "1.0-SNAPSHOT";

    @Getter private InetSocketAddress bindAddress;

    @Getter private final Logger logger;
    @Getter private final List<ModuleLoader> loaders;

    @Setter
    @Getter private boolean running = false;
    
    private long currentTick = 0;
    private long currentTaskId = 0;
    private final List<Player> players = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();

    public Server(InetSocketAddress bindAddress, Logger logger, List<ModuleLoader> loaders) {
        this.logger = logger;
        this.bindAddress = bindAddress;
        this.loaders = loaders;
    }

    private void loadModules() {

    }

    @Override
    public void run() {
        if(!running) return;
        logger.info("This server is running "+Server.SOFTWARE+" "+Server.SOFTWARE_VERSION+" on "+System.getProperty("os.name")+" "+System.getProperty("os.version"));

        loadModules();

        boolean exception = false;
        while(isRunning()) { // Begin ticking
            long start = System.currentTimeMillis();
            try {
                tick();
            } catch (Exception e) {
                logger.error("FATAL! "+e.getClass().getName()+" while ticking! "+e.getMessage());
                e.printStackTrace();
                exception = true;
            }
            long elapsed = System.currentTimeMillis() - start;
            if(elapsed > 50) { // 20 TPS
                logger.warn("Can't keep up! ("+elapsed+">50) Did the system time change or is the server overloaded?");
            } else {
                try {
                    Thread.sleep(50 - elapsed);
                } catch (InterruptedException e) {
                    logger.error("FATAL! failed to sleep while ticking! java.lang.InterruptedException: "+e.getMessage());
                    e.printStackTrace();
                    exception = true;
                }
            }
        }
        
        logger.info("Stopping server...");
        logger.info("Halting...");
        System.exit(exception ? 1 : 0);
    }

    /**
     * Add a task to run in a certain amount of ticks
     * @param ticks Amount of ticks to pass before the task runs.
     * @param r The <code>Runnable</code> to be ran.
     * @return The task that was added.
     */
    public synchronized Task addTask(long ticks, Runnable r) {
        return addTask(ticks, r, currentTaskId++, false, 0);
    }

    private Task addTask(long ticks, Runnable r, long taskId, boolean repeat, long interval) {
        synchronized (this.tasks) {
            Task t = new Task();
            t.taskId = taskId;
            t.runAt = this.currentTick + ticks;
            t.r = r;
            t.repeat = repeat;
            t.interval = interval;
            this.tasks.add(t);
            return t;
        }
    }

    /**
     * Add a task to repeatably run based on an interval of ticks.
     * @param interval The amount of ticks to pass between runs.
     * @param r The <code>Runnable</code> to be ran.
     * @return The task that was added.
     */
    public synchronized Task addRepeatingTask(long interval, Runnable r) {
        return addTask(1, r, currentTaskId++, true, interval);
    }

    public synchronized void cancelTask(long taskId) {
        final Task[] t = new Task[1];
        synchronized (this.tasks) {
            this.tasks.stream().filter(task -> task.taskId == taskId).forEach(task1 -> t[0] = task1);
            this.tasks.remove(t[0]);
        }
    }

    private void tick() throws Exception {
        if(this.tasks.isEmpty()) return;
        List<Task> toRemove = new ArrayList<>();
        synchronized (this.tasks) {
            this.tasks.stream().forEach(task -> {
                if (this.currentTick == task.runAt) {
                    task.r.run();
                    if(task.repeat) {
                        task.runAt = this.currentTick + task.interval;
                    } else toRemove.add(task);
                }
            });
            this.tasks.removeAll(toRemove);
        }
        currentTick++;
    }
}
