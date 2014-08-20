package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Moveable;
import net.blockserver.math.Vector3d;

public abstract class Entity extends Moveable
{

    protected Level level;
    protected double health;
	protected Entity(double x, double y, double z, Level level)
	{
        super(x, y, z);
        this.level = level;
    }

	protected Entity(Vector3d pos)
	{
	    super(pos);
	}

	public Level getLevel()
	{
	    return level;
	}

	public double getHealth()
	{
	    return health;
	}

	public void setHealth(double health)
	{
	    this.health = Math.min(Math.max(0, health), getMaxHealth());
	}

	public abstract EntityType getType();

	public abstract int getMaxHealth();

}
