package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Cow extends Entity{

    public Cow(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.COW;
    }

    @Override
    public int getMaxHealth(){
        return 5;
    }

}
