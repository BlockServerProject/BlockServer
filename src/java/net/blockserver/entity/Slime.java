package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Slime extends Entity{

    public Slime(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SLIME;
    }

    @Override
    public int getMaxHealth(){
        return 8;
    }

}
