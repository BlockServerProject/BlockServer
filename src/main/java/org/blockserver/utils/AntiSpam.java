/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
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
