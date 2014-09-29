package org.blockserver.entity;

import java.util.HashMap;
import java.util.Map;

public class SimpleEntityTypeManager implements EntityTypeManager{
	private Map<Byte, EntityType<?>> types;

	public SimpleEntityTypeManager(){
		types = new HashMap<Byte, EntityType<?>>();
		// TODO add types
	}

	@Override
	public EntityType<?> getType(Byte b){
		return types.get(b);
	}
}
