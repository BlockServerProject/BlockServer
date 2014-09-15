package org.blockserver.entity;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

/**
 * <p>This abstract superclass is the implementation of AI for entities that have brains.</p>
 * <p>Definition of "have brains":
 * <ul>
 * <li>Would wander around</li>
 * <li>Would look for targets</li>
 * <li>Would walk towards targets</li>
 * </ul>
 * </p>
 * <p>Note that according to the requirements above, since players are controlled
 * by clients, the Player class should not extend this class.
 */
public abstract class Living extends Entity{
	/**
	 * The modifier that increases an entity's vertical component when it jumps.
	 */
	public final static String MODIFIER_JUMP = "net.blockserver.entity.Living.jump";
	/**
	 * The modifier that decreases an entity's vertical component when it falls.
	 */
	public final static String MODIFIER_FALL = "net.blockserver.entity.Living.fall";
	/**
	 * The modifier that affects an entity's motion when it is in contact with flowing water.
	 */
	public final static String MODIFIER_WATER= "net.blockserver.entity.Living.water";
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
		if(isWandering()){
			Entity entity = pickTarget();
			if(entity != null){
				target = entity;
				setWalkingSpeed(getChasingSpeed());
			}
		}
		if(isWandering()){
			wander();
		}
		else{
			chaseTarget();
		}
		walk();
		onPostTickWalk();
	}

	protected Entity pickTarget(){
		return level.getClosestEntity(this, getTargetValidater());
	}
	protected Level.EntityValidate getTargetValidater(){
		return Level.validateInstance;
	}
	public boolean isWandering(){
		return wandering;
	}
	public void setWandering(boolean wandering){
		this.wandering = wandering;
	}
	public boolean hasTarget(){
		return target instanceof Entity;
	}
	public Vector3d getTarget(){
		return target;
	}
	public void setTarget(Vector3d target){
		this.target = target;
	}

	protected void wander(){
		// TODO
	}
	protected void chaseTarget(){
		updateYawPitchForTarget();
	}
	private void updateYawPitchForTarget(){
		YawPitchSet set = Vector3d.getRelativeYawPitch(this, target);
		setYaw(set.getYaw());
		setPitch(set.getPitch());
		broadcastMotion();
	}
	protected void walk(){
		// TODO physics
		// remember: the moving is done at Moveable.java, not here!
	}
	protected void onPostTickWalk(){
		if(getTargetCollisionDistance() <= target.distance(this)){
			onCollisionWithTarget();
		}
	}

	protected abstract void onCollisionWithTarget();
	/**
	 * <p>Get the entity's walking speed when it is wandering.</p>
	 * @return the entity's wandering speed.
	 */
	public abstract double getWanderingSpeed();
	/**
	 * <p>Get the entity's walking speed when it is chasing a target.</p>
	 * @return the entity's chasing speed.
	 */
	public abstract double getChasingSpeed();
	/**
	 * <p>Get the height of the entity.</p>
	 * @return the height of the entity.
	 */
	public abstract double getHeight();
	/**
	 * <p>Get the radius (in blocks) to scan for targets.<br>
	 * This distance is calculated in 3-D, not 2-D.</p>
	 * @return the absolute distance that a target must be to get chased.
	 */
	protected abstract double getScanTargetRadius();
	public double getTargetCollisionDistance(){
		return 0.5d;
	}
}
