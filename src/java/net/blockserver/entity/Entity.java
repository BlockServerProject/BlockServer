package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Moveable;
import net.blockserver.math.Vector3d;
import net.blockserver.math.YPSControlledVector3d;

public abstract class Entity extends Moveable
{

    private static int nextID = 0;
    protected int eid;
    protected Level level;
    protected double health;
    protected boolean hasYPSChanged = false; // YawPitchSpeed
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

    public Vector3d getInitialStdSpeed(){
        return new YPSControlledVector3d(0, 0, 0);
    }

    public Level getLevel()
    {
        return level;
    }

    public double getHealth()
    {
        return health;
    }

    public boolean isValid()
    {
        return level instanceof Level;
    }

    public void setHealth(double health)
    {
        this.health = Math.min(Math.max(0, health), getMaxHealth());
    }

    public void onTickUpdate()
    {
        super.onTickUpdate();
    }

    public void setYaw(double yaw){
        getYPS().setYaw(yaw);
    }

    public void setPitch(double pitch){
        getYPS().setPitch(pitch);
    }

    public void setWalkingSpeed(double speed){
        getYPS().setSpeed(speed);
    }

    public double getYaw(){
        return getYPS().getYaw();
    }

    public double getPitch(){
        return getYPS().getPitch();
    }

    public double getWalkingSpeed(){
        return getYPS().getSpeed();
    }

    public YPSControlledVector3d getYPS(){
        return (YPSControlledVector3d) getSpeed(MODIFIER_STANDARD);
    }

    public abstract EntityType getType();

    public abstract int getMaxHealth();

    public static int nextID()
    {
        return nextID++;
    }

}
