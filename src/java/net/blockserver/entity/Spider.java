package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Spider extends Entity{

    public Spider(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SPIDER;
    }

    @Override
    public int getMaxHealth(){
        return 10;
    }

}
