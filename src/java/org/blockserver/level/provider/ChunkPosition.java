package org.blockserver.level.provider;

import org.blockserver.math.Vector3;

public class ChunkPosition{
	private int x, z;
	private ChunkPosition(int x, int z){
		this.x = x;
		this.z = z;
	}

	@Override
	public boolean equals(Object other){
		if(other == null){
			return false;
		}
		if(!(other instanceof ChunkPosition)){
			return false;
		}
		return ((ChunkPosition) other).getX() == x && ((ChunkPosition) other).getZ() == z;
	}

	public int getX(){
		return x;
	}
	public int getZ(){
		return z;
	}

	public static ChunkPosition fromCoords(double x, double z){
		int X = (int) Math.round(x);
		int Z = (int) Math.round(z);
		return new ChunkPosition(X >> 4, Z >> 4);
	}
	public static ChunkPosition fromCoords(int x, int z){
		return new ChunkPosition(x >> 4, z >> 4);
	}
	public static ChunkPosition byDirectIndex(int x, int z){
		return new ChunkPosition(x, z);
	}

	public static ChunkPosition fromCoords(Vector3 coords){
		return fromCoords(coords.getX(), coords.getZ());
	}
}
