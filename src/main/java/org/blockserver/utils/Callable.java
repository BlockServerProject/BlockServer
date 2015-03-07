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
