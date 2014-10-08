package org.blockserver.level.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.blockserver.level.provider.LevelProvider;

public class GeneratorManager{
	private Map<String, Class<? extends Generator>> generators;
	public GeneratorManager(){
		generators = new HashMap<String, Class<? extends Generator>>(1);
	}
	public <T extends Generator> void addGenerator(String name, Class<T> generator) throws NoSuchMethodException{
		Constructor<T> c = generator.getConstructor(
				LevelProvider.class,
				long.class,
				Random.class,
				int.class,
				String.class
				);
		if(!c.isVarArgs()){
			throw new NoSuchMethodException("Doesn't receive varargs parameter");
		}
		if((c.getModifiers() & Modifier.PUBLIC) == 0){
			throw new NoSuchMethodException("Doesn't have public constructor");
		}
		generators.put(name, generator);
	}
	public Map<String, Class<? extends Generator>> getGenerators(){
		return generators;
	}
	public Object generate(String name, LevelProvider provider, long seed,
			Random random, int version, String args) throws Throwable{
		try{
			return generators.get(name).getConstructor(
					LevelProvider.class,
					long.class,
					Random.class,
					int.class,
					String.class)
					.newInstance(provider, seed, random, version, args);
		}
		catch(NoSuchMethodException e){
			// won't happen
			e.printStackTrace();
			return null;
		}
		catch(InvocationTargetException e){
			throw e.getCause();
		}
		catch(NullPointerException e){
			return false;
		}
	}
}
