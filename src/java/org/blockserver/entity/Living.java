package org.blockserver.entity;

import java.util.Random;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;
import org.blockserver.math.YPSControlledVector3d;

/**
 * <p>This abstract superclass is the implementation of AI for entities that have brains.</p>
 * <p>Definition of "have brains":
 * <ul>
 * <li>Would wander around</li>
 * <li>Would look for targets</li>
 * <li>Would walk towards targets</li>
 * </ul>
 * </p>
 */
public abstract class Living extends Entity {

    /**
     * The modifier that increases an entity's vertical component when it jumps.
     */
    public final static String MODIFIER_JUMP = "org.blockserver.entity.Living.jump";
    /**
     * The modifier that decreases an entity's vertical component when it falls.
     */
    public final static String MODIFIER_FALL = "org.blockserver.entity.Living.fall";
    /**
     * The modifier that affects an entity's motion when it is in contact with flowing water.
     */
    public final static String MODIFIER_WATER= "org.blockserver.entity.Living.water";

    /**
     * The percentage chance that the entity's yaw is slightly biased whilst chasing a target
     */
    public final static double BIAS_CHANCE = 10d;

    /**
     * The minimum number of ticks to ignore the target after yaw bias
     */
    public final static int MIN_BIAS_INTERVAL = 0;
    /**
     * The maximum number of ticks to ignore the target after yaw bias
     */
    public final static int MAX_BIAS_INTERVAL = 10;

    /**
     * The minimum yaw to bias
     */
    public final static double MIN_BIAS_YAW = 0d;
    /**
     * The maximum yaw to bias
     */
    public final static double MAX_BIAS_YAW = 10d;

    public final static double WANDER_CHANCE = 1d;

    private Vector3d target = null;

    private boolean wandering;

    private double currentYawMotion = 0, currentPitchMotion = 0;
    private int yawMotionTimeout = 0, pitchMotionTimeout = 0;

    private int ignoreTargetTimeout = 0;

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
        updateYawPitch();
        tickWalk();
        if(isWandering()){
            Entity entity = pickTarget();
            if(entity instanceof Entity){
                setTarget(entity);
            }
        }
        if(hasTarget()){
            if(yawMotionTimeout == 0){
                randomBias();
            }
            chaseTarget();
        }
        else{
            wander();
        }
    }

    private void updateYawPitch(){
        if(yawMotionTimeout > 0){
            yawMotionTimeout--;
            setYaw(getYaw() + currentYawMotion);
        }
        if(pitchMotionTimeout > 0){
            pitchMotionTimeout--;
            setPitch(getPitch() + currentPitchMotion);
        }
    }

    protected void tickWalk() {
        // TODO check modifiers
        super.onTickUpdate();
    }

    protected void randomBias(){
        Random psuedo = new Random();
        if(psuedo.nextDouble() * 100 < BIAS_CHANCE){
            double yaw = MIN_BIAS_YAW;
            double random = psuedo.nextDouble();
            yaw += (MAX_BIAS_YAW - MIN_BIAS_YAW) * random;
            if(psuedo.nextBoolean()){
                yaw *= -1;
            }
            int ticks = MIN_BIAS_INTERVAL;
            ticks += psuedo.nextInt(MAX_BIAS_INTERVAL - MIN_BIAS_INTERVAL);
            setYaw(getYaw() + yaw);
            ignoreTargetTimeout = ticks;
        }
    }

    protected void wander() {
        Random psuedo = new Random();
        if(psuedo.nextDouble() * 100 < WANDER_CHANCE){
            // TODO set yaw motion
        }
    }

    protected void chaseTarget(){
        if(ignoreTargetTimeout == 0){
            YPSControlledVector3d angles = (YPSControlledVector3d) getSpeed(MODIFIER_STANDARD);
            Vector3d.YawPitchSet set = target.subtract(this).getYawPitch(getChasingSpeed());
            angles.setYaw(set.getYaw());
            angles.setPitch(set.getPitch());
        }
    }

    public abstract double getChasingSpeed();

    public abstract double getHeight();

    protected abstract void broadcastMotion();

    public abstract double getMaxYawWanderDelta();

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
