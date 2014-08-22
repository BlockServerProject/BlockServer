package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Skeleton extends Entity{

    public Skeleton(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SKELETON;
    }

    @Override
    public int getMaxHealth(){
        return 10;
    }

}
