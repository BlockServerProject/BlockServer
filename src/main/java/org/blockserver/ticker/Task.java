package org.blockserver.ticker;

public abstract class Task{
	public abstract void onRun(long tick);
	public void onFinalize(){}
}
