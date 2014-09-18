package org.blockserver.math;

public class Vector3d{
	protected double x, y, z;

	public static Vector3d fromYawPitch(double yaw, double pitch){
		return fromYawPitch(yaw, pitch, 1);
	}
	public static Vector3d fromYawPitch(double yaw, double pitch, double speed){
		Vector3d subject = new Vector3d();
		setYawPitchOnSubject(yaw, pitch, speed, subject);
		return subject;
	}

	public static void setYawPitchOnSubject(double yaw, double pitch, Vector3d subject){
		setYawPitchOnSubject(yaw, pitch, 1, subject);
	}
	public static void setYawPitchOnSubject(double yaw, double pitch, double speed, Vector3d subject){
		yaw = (yaw + 90) * Math.PI / 180;
		pitch = pitch * Math.PI / 180;
		double y = -Math.sin(pitch) * speed;
		double horizDelta = Math.abs(Math.cos(pitch)) * speed;
		double x = -horizDelta * Math.sin(yaw);
		double z = horizDelta * Math.cos(yaw);
		subject.setX(x);
		subject.setY(y);
		subject.setZ(z);
		/*
		 * ===DRAFT===
		 * y = sin(pitch)
		 * horizDelta = |cos(pitch)|
		 * x = horizDelta * (-sin(yaw))
		 * z = horizDelta * cos(yaw)
		 */
	}
	public static YawPitchSet getRelativeYawPitch(Vector3d from, Vector3d to){
		YawPitchSet set = to.subtract(from).getYawPitch(from.distance(to));
		return set;
	}

	public Vector3d(){
		this(0, 0, 0);
	}
	public Vector3d(double v){
		this(v, v, v);
	}
	public Vector3d(double x, double y){
		this(x, y, 0);
	}
	public Vector3d(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public YawPitchSet getYawPitch(){
		return getYawPitch(1);
	}
	public YawPitchSet getYawPitch(double speed){
		double pitch = Math.asin(y / speed);
		double yaw = Math.acos((z / speed) / Math.abs(Math.cos(pitch)));
		return new YawPitchSet(yaw, pitch);
		/*
		 * ===DRAFT===
		 * pitch = asin(y)
		 * horizDelta = |cos(pitch)|
		 * cos(yaw) = z / horizDelta
		 * yaw = acos(z / horizDelta)
		 * 
		 */
	}

	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}

	public void setX(double v){
		this.x = v;
	}
	public void setY(double v){
		this.y = v;
	}
	public void setZ(double v){
		this.z = v;
	}
	public void setCoords(Vector3d v){
		setX(v.getX());
		setY(v.getY());
		setZ(v.getZ());
	}

	public Vector3d add(Vector3d delta){
		return merge(this, delta);
	}
	public Vector3d add(double dx, double dy, double dz){
		return new Vector3d(x + dx, y + dy, z + dz);
	}
	public Vector3d subtract(Vector3d delta){
		return add(delta.multiply(-1));
	}
	public Vector3d subtract(double dx, double dy, double dz){
		return add(-dx, -dy, -dz);
	}
	public Vector3d multiply(double k){
		return new Vector3d(x * k, y * k, z * k);
	}
	public Vector3d divide(double k){
		return multiply(1 / k);
	}

	public Vector3 floor(){
		return new Vector3((int) x, (int) y, (int) z);
	}
	public Vector3d abs(){
		return new Vector3d(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	public double distance(Vector3d other){
		Vector3d delta = subtract(other).abs();
		return Math.sqrt(Math.pow(delta.x, 2) + Math.pow(delta.y, 2) + Math.pow(delta.z, 2));
	}

	public static Vector3d merge(Vector3d... vectors){
		Vector3d base = new Vector3d();
		for(Vector3d vector: vectors){
			base = new Vector3d(base.getX() + vector.getX(), base.getY() + vector.getY(), base.getZ() + vector.getZ());
		}
		return base;
	}

	public static class YawPitchSet{
		private double yaw, pitch;
		public YawPitchSet(double yaw, double pitch){
			this.yaw = yaw;
			this.pitch = pitch;
		}
		public double getYaw(){
			return yaw;
		}
		public void setYaw(double yaw){
			this.yaw = yaw;
		}
		public double getPitch(){
			return pitch;
		}
		public void setPitch(double pitch){
			this.pitch = pitch;
		}
	}

	public double[] toArray(){
		return new double[]{x, y, z};
	}
}
