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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Callable implements Runnable{
	private Object object;
	private Method method;
	public Callable(Object object, String method) throws NoSuchMethodException{
		this.object = object;
		this.method = object.getClass().getMethod(method);
		for(Class<?> exType : this.method.getExceptionTypes()){
			try{
				exType.asSubclass(RuntimeException.class);
			}catch(ClassCastException e){
				try{
					exType.asSubclass(Error.class);
				}catch(ClassCastException e2){
					throw new IllegalArgumentException("The method throws unsupported exceptions");
				}
			}
		}
	}
	@Override
	public void run(){
		try{
			method.invoke(object);
		}catch(IllegalAccessException | InvocationTargetException e){
			if(e instanceof InvocationTargetException){
				Throwable t = e.getCause();
				if(t instanceof Error){
					throw (Error) t;
				}
				if(t instanceof RuntimeException){
					throw (RuntimeException) t;
				}
			}
			e.printStackTrace();
		}
	}
}
