package org.blockserver.level.format;

import org.blockserver.math.Vector3d;

public interface LevelProvider{
	public void init();
	public boolean loadChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public boolean saveChunk(ChunkPosition pos); // remember to mark this as synchronized in subclasses
	public boolean isChunkLoaded(ChunkPosition pos);
	public boolean isChunkGenerated(ChunkPosition pos);
	public boolean deleteChunk(ChunkPosition pos);
	public Vector3d getSpawn();

	public static class ChunkPosition{
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
	}
}
