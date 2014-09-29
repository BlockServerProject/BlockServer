package org.blockserver.entity;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.blockserver.Server;
import org.blockserver.io.bsf.BSFReader;
import org.blockserver.level.Level;

public interface EntityType<E extends SavedEntity>{
	public E read(BSFReader reader, Server server) throws IOException;

	public static class StandardSavedEntityType<T extends SavedEntity> implements EntityType<T>{
		protected Class<T> clazz;
		private Constructor<T> constructor;

		public StandardSavedEntityType(Class<T> clazz) throws NoSuchMethodException, IllegalArgumentException{
			this.clazz = clazz;
			setConstructor();
			if((clazz.getModifiers() & Modifier.ABSTRACT) != 0){
				throw new IllegalArgumentException("Cannot pass abstract type to StandardSavedEntityType");
			}
		}
		protected void setConstructor() throws NoSuchMethodException{
			this.constructor = clazz.getConstructor(double.class, double.class, double.class, Level.class);			
		}

		public T read(BSFReader reader, Server server) throws IOException{
			double[] v = {reader.readDouble(), reader.readDouble(), reader.readDouble()};
			Level level = server.getLevel(reader.readString(1), true, false);
			return instantiate(v[0], v[1], v[2], level, reader, server);
		}
		private T instantiate(double x, double y, double z, Level level, BSFReader reader, Server server){
			try{
				return constructor.newInstance(x, y, z, level);
			}
			catch(NullPointerException e){
				server.getLogger().error("Cannot use StandardSavedEntityType for %s, an entity type with no standard SavedEntity constructor", clazz.getName());
				return null;
			}
			catch(InstantiationException e){
				e.printStackTrace();
				return null; // this won't happen.
			}
			catch(IllegalAccessException e){
				server.getLogger().error("Standard SavedEntity of %s is not visible! Do not use StandardSavedEntityType in this case.", clazz.getName());
				return null;
			}
			catch(IllegalArgumentException e){
				e.printStackTrace();
				return null; // this shouldn't happen.
			}
			catch(InvocationTargetException e){
				server.getLogger().error("The following exception from standard SavedEntity constructor of class %s is thrown but cannot be caught gracefully:", clazz.getName());
				e.getCause().printStackTrace();
				return null;
			}
		}
	}
}
