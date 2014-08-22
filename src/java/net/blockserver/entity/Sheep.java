package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class Sheep extends Entity{

    private Dye dye;

    public Sheep(Vector3d pos, Level level)
    {
        super(pos, level);
        this.dye = Dye.Random();
    }

    public Sheep(Vector3d pos, Level level, Dye dye){
        super(pos, level);
        this.dye = dye;
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SHEEP;
    }

    @Override
    public int getMaxHealth(){
        return 4;
    }

}
