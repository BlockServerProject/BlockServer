package net.blockserver.scheduler;

import java.util.ArrayList;
import java.util.List;

public class Scheduler extends Thread
{
    public final int DEFAULT_TICKS = 20; // default ticks per seconds 20, like minecraft

    private int ids, sleepIntervals, currentTick;
    private boolean isRunning;
    private List<Task> tasks;

    public Scheduler()
    {
        this.ids = 0;
        this.sleepIntervals = (1000 / DEFAULT_TICKS);
        this.currentTick = 0;
        this.isRunning = false;
        this.tasks = new ArrayList<Task>();
    }

    public Scheduler(int tickPerSeconds)
    {
        this.ids = 0;
        this.sleepIntervals = (1000 / tickPerSeconds);
        this.currentTick = 0;
        this.isRunning = false;
        this.tasks = new ArrayList<Task>();
    }

    public int addTask(Task t)
    {
        if(t.isSetup())
        {
            t.setID(this.ids++);
            t.setDelay((t.getDelay() + this.currentTick));
            this.tasks.add(t);
            return  t.getID();
        }
        return -1;
    }

    public void removeTask(int id)
    {
        for (int i = 0; i < this.tasks.size(); i++)
        {
            if(this.tasks.get(i).getID() == id)
            {
                this.tasks.remove(i);
            }
        }
    }

    public void removeAllTask()
    {
        this.tasks.clear();
    }

    public void Start() throws Exception
    {
        if(this.isRunning)
            throw  new Exception("There is another scheduler class running!");

        this.isRunning = true;
        this.start();
    }

    public void run()
    {
        while (this.isRunning) {
            this.currentTick++;
            if (!this.tasks.isEmpty()) {
                for (int i = 0; i < this.tasks.size(); i++)
                {
                    Task t = this.tasks.get(i);
                    if(t == null) continue;

                    if (t.getDelay() == this.currentTick)
                    {
                        t.onRun(this.currentTick);
                        int times = t.getRepeatTimes();
                        if (times > 0)
                        {
                            if (--times == 0)
                            {
                                t.onFinish(currentTick);
                                this.tasks.remove(i);
                                continue;
                            }

                            t.setRepeatTimes(times);
                            t.setDelay(this.currentTick + t.getDeafultDelay());
                        }
                        else
                        {
                            t.onFinish(currentTick);
                            this.tasks.remove(i);
                        }
                    }
                }
            }

            try {
                Thread.sleep(this.sleepIntervals);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void Stop() throws Exception
    {
        if(!this.isRunning)
            throw new Exception("This scheduler is not running!");

        this.isRunning = false;
        this.join();
        this.tasks.clear();
    }
}