package org.blockserver.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.blockserver.Test;

public class Scheduler extends Thread{
	public final static int DEFAULT_TICKS = 20; // default ticks per seconds 20, like minecraft

	private int ids, sleepIntervals;
	private long currentTick;
	private boolean isRunning;
	private List<Task> tasks;

	public Scheduler(){
		this(1000 / DEFAULT_TICKS);
	}
	public Scheduler(int tickPerSeconds){
		ids = 0;
		sleepIntervals = (1000 / tickPerSeconds);
		currentTick = 0;
		isRunning = false;
		tasks = new ArrayList<Task>();
	}

	public int addTask(Task t){
		if(t.isSetup()){
			t.setID(ids++);
			t.setDelay((t.getDelay() + currentTick));
			tasks.add(t);
			return t.getID();
		}
		return -1;
	}

	public void removeTasks(int id){
		int i = 0;
		for(Task t: tasks){
			if(t.getID() == id){
				tasks.remove(i);
			}
			i++;
		}
	}
	public void removeAllTasks(){
		tasks.clear();
	}

	public void Start() throws Exception{
		if(isRunning){
			throw new RuntimeException("Scheduler is already running");
		}
		isRunning = true;
		start();
	}

	public void run(){
		while(isRunning) {
			currentTick++;
			if(!tasks.isEmpty()) {
				for(int i = 0; i < tasks.size(); i++){
					Task t = tasks.get(i);
					if(t == null){
						continue;
					}
					if(t.getDelay() == currentTick){
						t.onRun(currentTick);
						int times = t.getRepeatTimes();
						if(times > 0){
							if(--times == 0){
								t.onFinish(currentTick);
								tasks.remove(i);
								continue;
							}
							t.setRepeatTimes(times);
							t.setDelay(currentTick + t.getDefaultDelay());
						}
						else if(times == -1){ // -1 Repeat forever
							t.setDelay(currentTick + t.getDefaultDelay());
						}
						else{
							t.onFinish(currentTick);
							tasks.remove(i);
						}
					}
				}
			}

			try {
				Thread.sleep(sleepIntervals);
				if(Test.isTest() && currentTick > 10){
					try{
						end();
					}
					catch(RuntimeException e){}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void end() throws Exception{
		if(!isRunning){
			throw new RuntimeException("Cannot stop non-running scheduler!");
		}
		isRunning = false;
		join();
		tasks.clear();
	}

	public long getCurrentTick(){
		return currentTick;
	}
}
