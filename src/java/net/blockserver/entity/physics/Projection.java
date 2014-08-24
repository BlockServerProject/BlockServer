package net.blockserver.entity.physics;

import net.blockserver.entity.Entity;
import net.blockserver.math.Vector3d;

public class Projection
{
	public final static String MODIFIER_PROJECTION = "net.blockserver.entity.physics.Projection";
	protected Entity owner;
	public final double yaw, pitch;
	public final Vector3d initialDelta;
	protected double speed;
	protected int ticks = 0;
	public Projection(Entity owner, double yaw, double pitch, double speed)
	{
		this.yaw = yaw;
		this.pitch = pitch;
		this.speed = speed;
		this.owner = owner;
		Vector3d delta = initialDelta = Vector3d.fromYawPitch(yaw, pitch, 1);
		owner.setSpeed(delta.multiply(speed), MODIFIER_PROJECTION);
	}
	public void update(){
		ticks++;
		double gravity = owner.getLevel().getGravityAt(owner.floor());
		owner.setSpeed(owner.getSpeed(MODIFIER_PROJECTION).subtract(0, gravity, 0), MODIFIER_PROJECTION);
	}
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	public double getSpeed()
	{
		return speed;
	}
	public Entity getOwner()
	{
		return owner;
	}
}
