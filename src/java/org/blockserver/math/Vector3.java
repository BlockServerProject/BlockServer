package org.blockserver.math;

public class Vector3{
	private float x, y, z;

	public Vector3(float v){
		this(v, v, v);
	}
	public Vector3(float x, float y){
		this(x, y, 0);
	}
	public Vector3(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getZ(){
		return z;
	}

	public void setX(float v){
		this.x = v;
	}
	public void setY(float v){
		this.y = v;
	}
	public void setZ(float v){
		this.z = v;
	}
}
