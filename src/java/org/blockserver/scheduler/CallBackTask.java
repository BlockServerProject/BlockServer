package org.blockserver.scheduler;

import java.lang.reflect.Method;

public class CallBackTask extends Task{
	private Method callback;
	private Object clazz;

	public CallBackTask(Object c, String callback, int delay) throws NoSuchMethodException{
		super(delay);
		this.clazz = c;
		this.callback = c.getClass().getMethod(callback, int.class);
	}
	public CallBackTask(Object c, String callback, int delay, int repeatTimes) throws NoSuchMethodException{
		super(delay, repeatTimes);
		clazz = c;
		this.callback = c.getClass().getMethod(callback, int.class);
	}
	public CallBackTask(Object c, String callback, int delay, boolean repeatForever) throws NoSuchMethodException{
		super(delay, repeatForever);
		clazz = c;
		this.callback = c.getClass().getMethod(callback, int.class);
	}

	@Override
	public void onRun(int ticks){
		try{
			callback.invoke(clazz, ticks);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public final void onFinish(int ticks){} // Not used in Callback Task
}
