package org.blockserver.utils;

import java.util.HashMap;

public final class AntiSpam{
	private static HashMap<String, AntiSpam> map = new HashMap<>();
	private long timeout;
	private AntiSpam(long timeout){
		this.timeout = timeout;
	}
	private boolean canAct(){
		return timeout <= System.nanoTime();
	}
	public static void act(Runnable op, String key, long expireMillis){
		AntiSpam as = map.get(key);
		if(as != null){
			if(!as.canAct()){
				return;
			}
		}
		op.run();
		map.put(key, new AntiSpam(System.nanoTime() + expireMillis * 1000000));
	}
	public static boolean expire(String key){
		return map.remove(key) != null;
	}
}
