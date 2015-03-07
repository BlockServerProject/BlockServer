package org.blockserver.utils;

public class PositionDoublePrecision{
	private double x;
	private double y;
	private double z;

	/**
	 * TODO: Migrate integral usages of this class to {@link org.blockserver.utils.PositionInteger}
	 */
	@Deprecated
	public PositionDoublePrecision(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public PositionDoublePrecision(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
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

	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setZ(double z){
		this.z = z;
	}
}
