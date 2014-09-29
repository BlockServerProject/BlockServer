package org.blockserver.level.generator;

import java.util.HashMap;
import java.util.Map;

public class GeneratorManager{
	private Map<String, Class<? extends Generator>> generators;
	public GeneratorManager(){
		generators = new HashMap<String, Class<? extends Generator>>(1);
	}
	public void addGenerator(String name, Class<? extends Generator> generator){
		generators.put(name, generator);
	}
	public Map<String, Class<? extends Generator>> getGenerators(){
		return generators;
	}
}
