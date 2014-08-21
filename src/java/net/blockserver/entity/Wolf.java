package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Wolf extends Entity{

    public Wolf(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.WOLF;
    }

    @Override
    public int getMaxHealth(){
        return 4; //What's better for wolves' health, 4 hearts (wild) or 10 hearts (tamed)?
    }

}
