package org.blockserver.entity;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public abstract class SavedEntity extends Entity{
	public SavedEntity(double x, double y, double z, Level level){
		super(x, y, z, level);
	}
	public SavedEntity(double x, double y, double z, Level level, int eid){
		super(x, y, z, level, eid);
	}
	public SavedEntity(Vector3d pos, Level level){
		super(pos, level);
	}
	public SavedEntity(Vector3d pos, Level level, int eid){
		super(pos, level, eid);
	}
}
