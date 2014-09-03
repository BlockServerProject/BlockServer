package org.blockserver.math;

public class YPSControlledVector3d extends Vector3d{
	public final static String COORDS_EX = "Cannot modify coords of YPSControlledVector3d directly; use setYaw(), setPitch() and/or setSpeed()";

	protected double yaw, pitch, speed;
	private boolean canXYZChange = false;

	public YPSControlledVector3d(double yaw, double pitch){
		this(yaw, pitch, 1);
	}
	public YPSControlledVector3d(double yaw, double pitch, double speed){
		super(0, 0, 0);
		this.yaw = yaw;
		this.pitch = pitch;
		this.speed = speed;
		recalculate();
	}

	public void recalculate(){
		canXYZChange = true;
		setYawPitchOnSubject(yaw, pitch, speed, this);
		canXYZChange = false;
	}

	public double getYaw(){
		return yaw;
	}
	public double getPitch(){
		return pitch;
	}
	public double getSpeed(){
		return speed;
	}

	public void setYaw(double yaw){
		this.yaw = yaw;
		recalculate();
	}
	public void setPitch(double pitch){
		this.pitch = pitch;
		recalculate();
	}
	public void setSpeed(double speed){
		this.speed = speed;
		recalculate();
	}

	@Override
	public void setX(double x){
		if(!canXYZChange){
			throw new UnsupportedOperationException(COORDS_EX);
		}
	}
	@Override
	public void setY(double y){
		if(!canXYZChange){
			throw new UnsupportedOperationException(COORDS_EX);
		}
	}
	@Override
	public void setZ(double z){
		if(!canXYZChange){
			throw new UnsupportedOperationException(COORDS_EX);
		}
	}
}
