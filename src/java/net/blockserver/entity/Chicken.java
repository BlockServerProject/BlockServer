package net.blockserver.entity;

import net.blockserver.math.Vector3d;

public class Chicken extends Entity{

    public Chicken(Vector3d pos)
    {
        super(pos);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.CHICKEN;
    }

}
