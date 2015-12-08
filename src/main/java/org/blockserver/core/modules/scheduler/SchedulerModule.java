package org.blockserver.core.modules.scheduler;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * Written by Exerosis!
 */
public class SchedulerModule extends Module {
    @Getter private long currentTick;
    @Getter private final Map<Runnable, TaskData> tasks = new HashMap<>();

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
                    if (taskData.isAsync())
                        getServer().getExecutorService().execute(() -> entry.getKey().run());
                    else
                        entry.getKey().run();

                    if(taskData.getRepeatTimes() <= 0)
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
        registerTask(task, delay, 1, false);
    }

    public void registerTask(Runnable task, double delay, boolean async) {
        registerTask(task, delay, 1, async);
    }

    public void registerTask(Runnable task, int repeatTimes) {
        registerTask(task, 1, repeatTimes, false);
    }

    public void registerTask(Runnable task, int repeatTimes, boolean async) {
        registerTask(task, 1, repeatTimes, async);
    }

    public void registerTask(Runnable task, double delay, int repeatTimes) {
        registerTask(task, delay, repeatTimes, false);
    }

    public void registerTask(Runnable task, double delay, int repeatTimes, boolean async) {
        registerTask(task, new TaskData(delay, repeatTimes, async));
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

    public void setTaskAsync(Runnable task, boolean async) {
        getTaskData(task).setAsync(async);
    }

    public double getTaskDelay(Runnable task) {
        return getTaskData(task).getDelay();
    }

    public int getTaskRepeatTimes(Runnable task) {
        return getTaskData(task).getRepeatTimes();
    }

    public boolean isTaskAsync(Runnable task) {
        return getTaskData(task).isAsync();
    }

    public void cancelTask(Runnable task) {
        synchronized (tasks) {
            tasks.remove(task);
        }
    }
}
