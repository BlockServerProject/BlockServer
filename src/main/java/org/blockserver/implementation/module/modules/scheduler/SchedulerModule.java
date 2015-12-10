package org.blockserver.implementation.module.modules.scheduler;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * Written by Exerosis!
 */
public class SchedulerModule extends Module {
    @Getter private final Map<Runnable, TaskData> tasks = new HashMap<>();
    @Getter private long currentTick;

    public SchedulerModule(Server server) {
        super(server);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getExecutorService().execute(() -> {
            while (isEnabled()) {
                for (Map.Entry<Runnable, TaskData> entry : tasks.entrySet()) {
                    TaskData taskData = entry.getValue();
                    if (taskData.getNextTickTime() > System.currentTimeMillis())
                        continue;
                    taskData.repeatTimes--;
                    entry.getKey().run();

                    if (taskData.getRepeatTimes() <= 0)
                        tasks.remove(entry.getKey());
                    taskData.setLastTickTime(System.currentTimeMillis());
                }
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
        tasks.clear();
    }

    public void registerTask(Runnable task, double delay) {
        registerTask(task, delay, 1);
    }

    public void registerTask(Runnable task, int repeatTimes) {
        registerTask(task, 1, repeatTimes);
    }

    public void registerTask(Runnable task, double delay, int repeatTimes) {
        registerTask(task, new TaskData(delay, repeatTimes));
    }

    public void registerTask(Runnable task, TaskData taskData) {
        synchronized (tasks) {
            tasks.put(task, taskData);
        }
    }

    public TaskData getTaskData(Runnable task) {
        synchronized (tasks) {
            return tasks.get(task);
        }
    }

    public void setTaskData(Runnable task, TaskData taskData) {
        synchronized (tasks) {
            tasks.put(task, taskData);
        }
    }

    public void setTaskDelay(Runnable task, double delay) {
        getTaskData(task).setDelay(delay);
    }

    public void setTaskRepeatTimes(Runnable task, int repeatTimes) {
        getTaskData(task).setRepeatTimes(repeatTimes);
    }

    public double getTaskDelay(Runnable task) {
        return getTaskData(task).getDelay();
    }

    public int getTaskRepeatTimes(Runnable task) {
        return getTaskData(task).getRepeatTimes();
    }

    public void cancelTask(Runnable task) {
        synchronized (tasks) {
            tasks.remove(task);
        }
    }
}
