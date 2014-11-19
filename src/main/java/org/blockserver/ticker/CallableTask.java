package org.blockserver.ticker;

import org.blockserver.utils.Callable;

public class CallableTask extends Task{
	private Callable callable;
	public CallableTask(Object object, String method, Class<?>... types) throws NoSuchMethodException{
		this(new Callable(object, method, types));
	}
	public CallableTask(Callable callable){
		this.callable = callable;
	}
	@Override
	public void onRun(long tick){
		callable.run();
	}
}
