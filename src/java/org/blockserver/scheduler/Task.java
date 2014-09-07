package org.blockserver.scheduler;

public abstract class Task{
	private int id, defaultDelay, repeatTimes;
	private long delay;
	private boolean repeat, isSetup = false;

	public Task(int delay){
		this.delay = delay;
		this.defaultDelay = 0;
		this.repeatTimes = 0;
		this.repeat = false;
		this.isSetup = true;
	}
	public Task(int delay, int repeatTimes){
		this.delay = delay;
		this.defaultDelay = delay;
		this.repeat = true;
		this.repeatTimes = repeatTimes;
		this.isSetup = true;
	}
	public Task(int delay, boolean repeatForever){
		this.delay = delay;
		this.defaultDelay = delay;
		this.repeat = repeatForever;
		this.repeatTimes = (repeatForever ? -1 : 0);
		this.isSetup = true;
	}

	public abstract void onRun(long currentTick);
	public abstract void onFinish(long currentTick);

	public int getID(){
		return id;
	}
	public void setID(int id){
		this.id = id;
	}

	public long getDelay(){
		return delay;
	}
	public void setDelay(long delay){
		this.delay = delay;
	}

	public int getRepeatTimes(){
		if(repeat){
			return repeatTimes;
		}
		else{
			return -1;
		}
	}
	public void setRepeatTimes(int times){
		if(times == 0 && repeat){
			repeat = false;
			repeatTimes = times;
		}
		else{
			repeat = true;
			repeatTimes = times;
		}
	}

	public int getDefaultDelay(){
		return defaultDelay;
	}

	public boolean isSetup(){
		return isSetup;
	}
}
