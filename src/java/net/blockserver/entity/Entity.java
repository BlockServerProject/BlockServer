package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Moveable;
import net.blockserver.math.Vector3d;

public abstract class Entity extends Moveable
{

    private static int nextID = 0;
    protected int eid;
    protected Level level;
    protected double health;
    protected Entity(double x, double y, double z, Level level)
    {
        super(x, y, z);
        this.level = level;
        eid = nextID();
    }

    protected Entity(Vector3d pos, Level level)
    {
        super(pos);
        this.level = level;
    }

    public Level getLevel()
    {
        return level;
    }

    public double getHealth()
    {
        return health;
    }

    public boolean isValid(){
        return level instanceof Level;
    }

    public void setHealth(double health)
    {
        this.health = Math.min(Math.max(0, health), getMaxHealth());
    }

    public abstract EntityType getType();

    public abstract int getMaxHealth();

    public static int nextID(){
        return nextID++;
    }

}
