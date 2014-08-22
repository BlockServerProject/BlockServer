package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Silverfish extends Entity{

    public Silverfish(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SILVERFISH;
    }

    @Override
    public int getMaxHealth(){
        return 4;
    }

}
