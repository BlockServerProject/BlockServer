package org.blockserver.ticker;

import java.util.ArrayList;

import org.blockserver.Info;
import org.blockserver.Server;
import org.blockserver.utils.AntiSpam;

public class ServerTicker{
	private static final String ANTISPAM_LOAD_MEASURE_TOO_HIGH = "org.blockserver.ticker.ServerTicker.LoadMeasureTooHigh";
	private Server server;
	private long sleep;
	private long tick = -1L;
	private boolean running = false;
	private boolean lastTickDone = false;
	private long lastTickMilli;
	private double loadMeasure = 0D;
	private long startTime;
	private final ArrayList<RegisteredTask> tasks = new ArrayList<>();

	public ServerTicker(Server server, int sleepNanos){
		this.server = server;
		this.sleep = sleepNanos;
	}
	public void start(){
		if(running){
			throw new IllegalStateException("Ticker is already running");
		}
		running = true;
		server.getLogger().info("%s %s is now running.", Info.SOFTWARE_NAME, Info.VERSION_STRING());
		startTime = System.currentTimeMillis();
		while(running){
			lastTickMilli = System.currentTimeMillis();
			tick++;
			tick();
			// calculate server load
			long now = System.currentTimeMillis();
			long diff = now - lastTickMilli;
			loadMeasure = diff * 100D /  sleep;
			if(loadMeasure > 80D){
				AntiSpam.act(new Runnable(){
					@Override
					public void run(){
						server.getLogger().warning("The server load is too high! (%f / 100)", loadMeasure);
					}
				}, ANTISPAM_LOAD_MEASURE_TOO_HIGH, 5000);
				continue;
			}
			long need = sleep - diff;
			try{
				Thread.sleep(need);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		synchronized(tasks){
			for(RegisteredTask task: tasks){
				task.getTask().onFinalize();
			}
		}
		lastTickDone = true;
	}
	private void tick(){
		RegisteredTask[] taskArray;
		synchronized(tasks){
			taskArray = new RegisteredTask[tasks.size()];
			tasks.toArray(taskArray);
		}
		for(RegisteredTask task: taskArray){
			task.check(tick);
		}
	}
	public void stop(){
		if(!running){
			throw new IllegalStateException("Ticker is not running and cannot be stopped");
		}
		running = false;
		while(!lastTickDone);
	}

	public void addDelayedTask(Task task, int delay){
		synchronized(tasks){
			tasks.add(RegisteredTask.delay(task, delay));
		}
	}
	public void addRepeatingTask(Task task, int repeatInterval){
		synchronized(tasks){
			tasks.add(RegisteredTask.repeat(task, repeatInterval));
		}
	}
	public void addDelayedRepeatingTask(Task task, int delay, int repeatInterval){
		synchronized(tasks){
			tasks.add(RegisteredTask.delayAndRepeat(task, delay, repeatInterval));
		}
	}
	public boolean cancelTask(Task task){
		RegisteredTask corr = null;
		synchronized(tasks){
			for(RegisteredTask rt: tasks){
				if(rt.getTask().equals(task)){
					corr = rt;
					break;
				}
			}
			return tasks.remove(corr);
		}
	}
	public long getStartTime(){
		return startTime;
	}
}
