package org.blockserver.level;

public class ChunkPosition{
	private int x;
	private int z;

	public ChunkPosition(int x, int z){
		this.x = x;
		this.z = z;
	}

	public int getX(){
		return x;
	}
	public int getZ(){
		return z;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ChunkPosition)){
			return false;
		}
		ChunkPosition other = (ChunkPosition) obj;
		return other.getX() == getX() && other.getZ() == getZ();
	}
}
