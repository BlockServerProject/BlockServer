package org.blockserver.level.format;

import java.io.File;

public abstract class GenericLevelProvider{
	private boolean LOCK = false;
	public GenericLevelProvider(File file){
		
	}
	protected boolean LOCK(){
		if(LOCK){
			return false;
		}
		LOCK = true;
		return true;
	}
	protected boolean UNLOCK(){
		if(!LOCK){
			return false;
		}
		LOCK = false;
		return true;
	}
}
