package net.blockserver.entity;

import net.blockserver.math.Moveable;
import net.blockserver.math.Vector3d;

public abstract class Entity extends Moveable{

	protected Entity(double x, double y, double z) {
        super(x, y, z);
    }

	protected Entity(Vector3d pos){
	    super(pos);
	}

    public abstract EntityType getType();

}
