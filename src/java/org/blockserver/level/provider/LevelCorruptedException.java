package org.blockserver.level.provider;

import java.io.IOException;

public class LevelCorruptedException extends IOException{
	private static final long serialVersionUID = -7265102281322288989L;
	private LevelProvider level;
	private boolean affectingWhole;
	public LevelCorruptedException(boolean affectsWhole, String message, LevelProvider level){
		super(message);
		this.affectingWhole = affectsWhole;
		this.level = level;
	}
	public LevelCorruptedException(boolean affectsWhole, Throwable e, LevelProvider level){
		super(e);
		this.affectingWhole = affectsWhole;
		this.level = level;
	}
	public LevelProvider getLevelProvider(){
		return level;
	}
	public boolean isAffectingWhole(){
		return affectingWhole;
	}
}
