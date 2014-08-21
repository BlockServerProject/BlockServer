package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;
import net.blockserver.math.YPSControlledVector3d;

/**
 * Class Living extends Entity
 *
 * This abstract superclass is the implementation of AI for entities that have brains.
 *
 * Definition of "have brains":
 * * Would wander around
 * * Would look for targets
 * * Would walk towards targets
 */
public abstract class Living extends Entity {

    public final static String MODIFIER_JUMP = "net.blockserver.entity.Living.jump",
            MODIFIER_FALL = "net.blockserver.entity.Living.fall",
            MODIFIER_PITCH_NEUTRALIZER = "net.blockserver.entity.Living.neutralizer.pitch",
            MODIFIER_WATER= "net.blockserver.entity.Living.water";

    public final static double WANDER_PERCENTAGE = 0.03;

    private Vector3d target = null;

    private boolean wandering;

    public Living(double x, double y, double z, Level level) {
        super(x, y, z, level);
        initEntity();
    }

    public Living(Vector3d pos, Level level) {
        super(pos, level);
        initEntity();
    }

    protected void initEntity(){
        setSpeed(new Vector3d(), MODIFIER_JUMP);
        setSpeed(new Vector3d(), MODIFIER_FALL);
        setSpeed(new Vector3d(), MODIFIER_WATER);
    }

    public void onTickUpdate(){
        tickWalk();
        if(isWandering()){
            Entity entity = pickTarget();
            if(entity instanceof Entity){
                setTarget(entity);
            }
        }
        if(hasTarget()){
            chaseTarget();
        }
        else{
            wander();
        }
    }

    protected void tickWalk() {
        // TODO check modifiers
        super.onTickUpdate();
    }

    protected void wander() {
        if(Math.random() < WANDER_PERCENTAGE){
            
        }
    }

    protected void chaseTarget(){
        YPSControlledVector3d angles = (YPSControlledVector3d) getSpeed(MODIFIER_STANDARD);
        Vector3d.YawPitchSet set = target.subtract(this).getYawPitch(getChasingSpeed());
        angles.setYaw(set.getYaw());
        angles.setPitch(set.getPitch());
    }

    public abstract double getChasingSpeed();

    public abstract double getHeight();

    protected abstract void broadcastMotion();

    protected Entity pickTarget(){
        return level.getClosestEntity(this, getTargetValidater());
    }

    protected Level.EntityValidate getTargetValidater(){
        return Level.validateInstance;
    }

    protected abstract double getScanTargetRadius();

    public boolean isWandering() {
        return wandering;
    }

    public void setWandering(boolean wandering) {
        this.wandering = wandering;
    }

    public boolean hasTarget(){
        return target instanceof Entity;
    }

    public Vector3d getTarget() {
        return target;
    }

    public void setTarget(Vector3d target) {
        this.target = target;
    }

}
