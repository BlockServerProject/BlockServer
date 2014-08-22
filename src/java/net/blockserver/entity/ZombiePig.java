package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class ZombiePig extends Entity{

    public ZombiePig(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ZOMBIE_PIG;
    }

    @Override
    public int getMaxHealth(){
        return 10;
    }

}
