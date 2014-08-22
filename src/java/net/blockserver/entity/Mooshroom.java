package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Mooshroom extends Entity{

    public Mooshroom(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.MOOSHROOM;
    }

    @Override
    public int getMaxHealth(){
        return 5;
    }

}
