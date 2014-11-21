package org.blockserver.ticker;

import java.util.ArrayList;

import org.blockserver.Server;
import org.blockserver.utils.AntiSpam;

public class ServerTicker{
	private static final String ANTISPAM_LOAD_MEASURE_TOO_HIGH = "org.blockserver.ticker.ServerTicker.LoadMeasureTooHigh";
	private Server server;
	private long sleep;
	private long tick = -1L;
	private boolean running = false;
	private boolean lastTickDone = false;
	private long lastTickNano;
	private double loadMeasure = 0D;
	private long startTime;
	private ArrayList<RegisteredTask> tasks = new ArrayList<RegisteredTask>();

	public ServerTicker(Server server, int sleepNanos){
		this.server = server;
		this.sleep = sleepNanos * 1000000L;
	}
	public void start(){
		if(running){
			throw new IllegalStateException("Ticker is already running");
		}
		running = true;
		startTime = System.currentTimeMillis();
		lastTickNano = System.currentTimeMillis(); //System.nanoTime(); Fix...
		while(running){
			tick++;
			tick();
			// calculate server load
			long now = System.currentTimeMillis(); //System.nanoTime(); Fix...
			long diff = now - lastTickNano;
			loadMeasure = diff * 100D /  sleep;
			if(loadMeasure > 80D){
				AntiSpam.act(new Runnable(){
					@Override
					public void run(){
						server.getLogger().warning("The server load is too high! (%f / 100)", loadMeasure);
					}
				}, ANTISPAM_LOAD_MEASURE_TOO_HIGH, 5000);
			}
			long need = (sleep - diff) * 1000000L;
			int nanos = (int) (need % 1000000L);
			while(nanos < 0){
				nanos += 1000000;
			}
			int millis = (int) ((need - nanos) / 1000000L);
			try{
				Thread.sleep(millis, nanos);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		for(RegisteredTask task: tasks){
			task.getTask().onFinalize();
		}
		lastTickDone = true;
	}
	private void tick(){
		for(RegisteredTask task: tasks){
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
		synchronized(tasks){
			return tasks.remove(task);
		}
	}
	public long getStartTime(){
		return startTime;
	}
}
