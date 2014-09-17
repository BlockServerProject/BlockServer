package org.blockserver.entity;

import java.util.Locale;

import org.blockserver.Server;
import org.blockserver.level.Level;
import org.blockserver.math.Moveable;
import org.blockserver.math.Vector3d;
import org.blockserver.math.YPSControlledVector3d;

public abstract class Entity extends Moveable{
	/**
	 * The modifier that neutralizes an entity's vertical component since
	 * an entity's pitch isn't in variation with its vertical speed
	 */
	public final static String MODIFIER_PITCH_NEUTRALIZER = "net.blockserver.entity.Entity.neutralizer.pitch";

	private static int nextID = 0;
	protected Throwable trace;
	protected int eid;
	protected Level level;
	protected double health;
	protected boolean hasYPSChanged = false; // YawPitchSpeed
	protected Entity(double x, double y, double z, Level level){
		super(x, y, z);
		this.level = level;
		trace = new Throwable("Debug stack trace");
		eid = nextID();
		setSpeed(new YPSControlledVector3d(0, 0, 0), MODIFIER_PITCH_NEUTRALIZER);
	}
	protected Entity(Vector3d pos, Level level){
		this(pos.getX(), pos.getY(), pos.getZ(), level);
	}

	public Vector3d getInitialStdSpeed(){
		return new YPSControlledVector3d(0, 0, 0);
	}
	public EntityType getType(){
		return EntityType.valueOf(this.getClass().getSimpleName().toUpperCase());
	}
	public Level getLevel(){
		return level;
	}
	public double getHealth(){
		return health;
	}
	public void setHealth(double health){
		this.health = Math.min(Math.max(0, health), getMaxHealth());
	}
	public boolean isValid(){
		return level instanceof Level;
	}
	public void validate(){
		if(!isValid()){
			throw new IllegalStateException("Level field of Entity not initialized", trace);
		}
	}

	public void setYaw(double yaw){
		getYPS().setYaw(yaw);
	}
	public void setPitch(double pitch){
		getYPS().setPitch(pitch);
		getPitchNeutralizer().setPitch(-pitch);
	}
	public void setWalkingSpeed(double speed){
		getYPS().setSpeed(speed);
		getPitchNeutralizer().setSpeed(speed);
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

	/**
	 * Get the yaw, pitch and speed store of the entity.
	 * <p><strong>Warning: do not modify speed and pitch directly.
	 * The pitch needs neutralization to avoid the entity flying up/down.</strong></p>
	 * @return <code>YPSControlledVector3d</code> The yaw, pitch and speed store of the entity
	 */
	protected YPSControlledVector3d getYPS(){
		return (YPSControlledVector3d) getSpeed(MODIFIER_STANDARD);
	}
	private YPSControlledVector3d getPitchNeutralizer(){
		return (YPSControlledVector3d) getSpeed(MODIFIER_PITCH_NEUTRALIZER);
	}

	@Override
	public void onTickUpdate(){
		validate();
		super.onTickUpdate();
	}

	@Override
	public void start(Server server){
		validate();
		super.start(server);
	}

	public abstract int getMaxHealth();
	public void setMaxHealth(int health){
		throw new UnsupportedOperationException(String.format(Locale.US, "Entity %s does not support modification of maximum health", getClass().getSimpleName()));
	}

	/**
	 * Send MCPE protocol data packets to broadcast changes in the entity's motion
	 * Note that the parameters of this method may need changes to meet the MCPE protocol.
	 * If so, just change it :)
	 */
	protected abstract void broadcastMotion();

	public static int nextID(){
		return nextID++;
	}
}
