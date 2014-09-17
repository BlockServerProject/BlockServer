package org.blockserver.scheduler;

import java.lang.reflect.Method;

import org.blockserver.Server;

public class CallbackTask extends Task{
	private Method callback;
	private Object clazz;
	private Throwable trace;

	public CallbackTask(Object c, String callback, int delay) throws NoSuchMethodException{
		super(delay);
		this.clazz = c;
		this.callback = c.getClass().getMethod(callback, int.class);
		this.trace = new Throwable("CallbackTask stack trace");
	}
	public CallbackTask(Object c, String callback, int delay, int repeatTimes) throws NoSuchMethodException{
		super(delay, repeatTimes);
		clazz = c;
		this.callback = c.getClass().getMethod(callback, long.class);
	}
	public CallbackTask(Object c, String callback, int delay, boolean repeatForever) throws NoSuchMethodException{
		super(delay, repeatForever);
		clazz = c;
		this.callback = c.getClass().getMethod(callback, long.class);
	}

	@Override
	public void onRun(Server server, long ticks){
		try{
			callback.invoke(clazz, ticks);
		}
		catch(Exception e){
			server.getLogger().info("Error during invoking callback:");
			synchronized(this){
				e.printStackTrace();
			}
			server.getLogger().info("Callback: %s.%s()", clazz.getClass().getName(), callback.getName());
			server.getLogger().info("Trace: ");
			trace.printStackTrace();
		}
	}

	@Override
	public final void onFinish(long ticks){} // Not used in Callback Task
}
