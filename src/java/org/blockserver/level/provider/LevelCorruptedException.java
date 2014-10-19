package org.blockserver.level.provider;

import java.io.IOException;

public class LevelCorruptedException extends IOException{
	private static final long serialVersionUID = -7265102281322288989L;
	private LevelProvider level;
	public LevelCorruptedException(String message, LevelProvider level){
		super(message);
		this.level = level;
	}
	public LevelCorruptedException(Throwable e, LevelProvider level){
		super(e);
		this.level = level;
	}
	public LevelProvider getLevelProvider(){
		return level;
	}
}
