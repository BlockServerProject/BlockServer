package org.blockserver.utils;

import org.blockserver.level.ChunkPosition;

public class PositionDoublePrecision{
	private double x;
	private double y;
	private double z;

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
	
	//Some Useful Tools for Plugin Devs
	
	public void increaseX(Integer count){
		this.x = x + count;
	}
	public void increaseY(Integer count){
		this.y = y + count;
	}
	public void increaseZ(Integer count){
		this.z = z + count;
	}

	public double[] getArray(){
		return new double[]{
			this.x,
			this.y,
			this.z
		};
	}

	public ChunkPosition getChunkPos(){
		return new ChunkPosition(((int) x) >> 4, ((int) z) >> 4);
	}
}
