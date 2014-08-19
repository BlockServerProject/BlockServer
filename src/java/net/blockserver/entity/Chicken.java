package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Chicken extends Entity{

    public Chicken(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.CHICKEN;
    }

    @Override
    public int getMaxHealth(){
        return 4;
    }

}
