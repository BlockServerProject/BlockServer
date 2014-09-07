package org.blockserver.scheduler;

public abstract class Task{
	private int id, delay, defaultDelay, repeatTimes;
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

	public abstract void onRun(int ticks);
	public abstract void onFinish(int ticks);

	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}

	public int getDelay(){
		return delay;
	}
	
	public void setDelay(int delay){
		this.delay = delay;
	}

	public int getRepeatTimes(){
		return (repeat) ? repeatTimes : -1;
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
