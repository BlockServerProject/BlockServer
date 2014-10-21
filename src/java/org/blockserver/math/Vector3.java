package org.blockserver.math;

public class Vector3{
	private int x, y, z;

	public Vector3(int v){
		this(v, v, v);
	}
	public Vector3(int x, int y){
		this(x, y, 0);
	}
	public Vector3(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX(){
		return x;
	}
	public byte getChunkX(){
		return (byte) (x & 0x0F);
	}
	public byte getChunkY(){
		return (byte) (y & 0x0F);
	}
	public byte getChunkZ(){
		return (byte) (z & 0x0F);
	}
	public int getY(){
		return y;
	}
	public int getZ(){
		return z;
	}

	public void setX(int v){
		this.x = v;
	}
	public void setY(int v){
		this.y = v;
	}
	public void setZ(int v){
		this.z = v;
	}

	public Vector3 add(Vector3 delta){
		return merge(this, delta);
	}
	public Vector3 add(int dx, int dy, int dz){
		return new Vector3(x + dx, y + dy, z + dz);
	}
	public Vector3 subtract(Vector3 delta){
		return add(delta.multiply(-1));
	}
	public Vector3 subtract(int dx, int dy, int dz){
		return add(-dx, -dy, -dz);
	}
	public Vector3 multiply(int k){
		return new Vector3(x * k, y * k, z * k);
	}
	public Vector3 divide(int k){
		return multiply(1 / k);
	}

	public Vector3 floor(){
		return new Vector3((int) x, (int) y, (int) z);
	}
	public Vector3 abs(){
		return new Vector3(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	public double length(){
		return Math.sqrt(Math.pow(x, 2) +  Math.pow(y, 2) + Math.pow(z, 2));
	}
	public double distance(Vector3 other){
		Vector3 delta = subtract(other).abs();
		return delta.length();
	}



	public static Vector3 merge(Vector3... vectors){
		int x = 0;
		int y = 0;
		int z = 0;
		for(Vector3 vector: vectors){
			x += vector.x;
			y += vector.y;
			z += vector.z;
		}
		return new Vector3(x, y, z);
	}
}
