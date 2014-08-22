package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Zombie extends Entity{

    public Zombie(Vector3d pos, Level level)
    {
        super(pos, level);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ZOMBIE;
    }

    @Override
    public int getMaxHealth(){
        return 10;
    }

}
