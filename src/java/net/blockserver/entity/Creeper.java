package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Creeper extends Entity{

    public Creeper(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.CREEPER;
    }

    @Override
    public int getMaxHealth(){
        return 10;
    }

}
