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
package org.blockserver.ticker;

import org.blockserver.utils.Callable;

public class CallableTask extends Task{
	private Callable callable;
	public CallableTask(Object object, String method) throws NoSuchMethodException{
		this(new Callable(object, method));
	}
	public CallableTask(Callable callable){
		this.callable = callable;
	}
	@Override
	public void onRun(long tick){
		callable.run();
	}
}
