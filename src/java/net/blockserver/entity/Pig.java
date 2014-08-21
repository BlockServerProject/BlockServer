package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Pig extends Entity{

    public Pig(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.PIG;
    }

    @Override
    public int getMaxHealth(){
        return 5;
    }

}
