package net.blockserver.entity;

import net.blockserver.level.Level;
import net.blockserver.math.Moveable;
import net.blockserver.math.Vector3d;
import net.blockserver.math.YPSControlledVector3d;

public abstract class Entity extends Moveable
{
	/**
	 * The modifier that neutralizes an entity's vertical component since
	 * an entity's pitch isn't in variation with its vertical speed
	 */
	public final static String MODIFIER_PITCH_NEUTRALIZER = "net.blockserver.entity.Entity.neutralizer.pitch";

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
		this(pos.getX(), pos.getY(), pos.getZ(), level);
		setSpeed(new YPSControlledVector3d(0, 0, 0), MODIFIER_PITCH_NEUTRALIZER);
	}

	public Vector3d getInitialStdSpeed()
	{
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

	private YPSControlledVector3d getYPS(){
		return (YPSControlledVector3d) getSpeed(MODIFIER_STANDARD);
	}

	private YPSControlledVector3d getPitchNeutralizer(){
		return (YPSControlledVector3d) getSpeed(MODIFIER_PITCH_NEUTRALIZER);
	}

	public EntityType getType(){
		return EntityType.valueOf(this.getClass().getSimpleName().toUpperCase());
	}

	public abstract int getMaxHealth();

	public static int nextID()
	{
		return nextID++;
	}

}