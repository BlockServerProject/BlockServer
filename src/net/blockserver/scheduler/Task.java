package net.blockserver.scheduler;

public abstract class Task
{
    private int id, delay, deafultDelay, repeatTimes;
    private boolean repeat, isSetup = false;

    public Task(int delay)
    {
        this.delay = delay;
        this.deafultDelay = 0;
        this.repeatTimes = 0;
        this.repeat = false;
        this.isSetup = true;
    }

    public Task(int delay, int repeatTimes)
    {
        this.delay = delay;
        this.deafultDelay = delay;
        this.repeat = true;
        this.repeatTimes = repeatTimes;
        this.isSetup = true;
    }

    public abstract void onRun(int ticks);
    public abstract void onFinish(int ticks);

    public void setID(int id)
    {
        this.id = id;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public void setRepeatTimes(int times)
    {
        if(times == 0 && repeat)
        {
            this.repeat = false;
            this.repeatTimes = times;
        }
        else
        {
            this.repeat = true;
            this.repeatTimes = times;
        }
    }

    public int getRepeatTimes()
    {
        if(this.repeat)
            return this.repeatTimes;
        else
            return -1;
    }

    public int getDelay()
    {
        return this.delay;
    }

    public int getDeafultDelay() { return this.deafultDelay; }

    public int getID()
    {
        return this.id;
    }

    public boolean isSetup()
    {
        return this.isSetup;
    }

}