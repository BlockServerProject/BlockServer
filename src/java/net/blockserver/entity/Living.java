package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

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

    private Vector3d target = null;

    private boolean wandering;

    public Living(double x, double y, double z, Level level) {
        super(x, y, z, level);
    }

    public Living(Vector3d pos, Level level) {
        super(pos, level);
    }

    public void onTickUpdate(){
        super.onTickUpdate();
        if(isWandering()){
            Entity entity = pickTarget();
            if(entity instanceof Entity){
                setTarget(entity);
            }
        }
        if(hasTarget()){
            chaseTarget();
        }
    }

    protected void chaseTarget(){
        
    }

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
