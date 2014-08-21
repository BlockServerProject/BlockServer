package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Enderman extends Entity{

    public Enderman(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ENDERMAN;
    }

    @Override
    public int getMaxHealth(){
        return 20;
    }

}
