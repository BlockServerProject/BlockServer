package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Villager extends Entity{

    public Villager(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.VILLAGER;
    }

    @Override
    public int getMaxHealth(){
        return 10;
    }

}
