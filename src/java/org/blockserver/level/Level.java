package org.blockserver.level;

import java.util.ArrayList;
import java.util.List;

import org.blockserver.blocks.Block;
import org.blockserver.entity.Entity;
import org.blockserver.level.format.IChunk;
import org.blockserver.level.format.ILevel;
import org.blockserver.math.Vector3;
import org.blockserver.math.Vector3d;

public class Level implements ILevel{
	public final static EntityValidate validateInstance = new Level.DummyValidate();

	private String name, path;
	private IChunk[] chunks;
	private int seed;
	private int defaultGamemode;
	private Vector3d spawnPos;
	private List<Entity> entities = new ArrayList<Entity>();

	public Level(String name, String path, int seed, int defaultGamemode, Vector3d spawnPos){
		this.name = name;
		this.path = path;
		this.seed = seed;
		this.defaultGamemode = defaultGamemode;
		this.spawnPos = spawnPos;
	}

	@Override
	public String getName(){
		return name;
	}
	@Override
	public String getPath(){
		return path;
	}

	@Override
	public boolean equals(Object other){
		if(other instanceof Level){
			return ((Level) other).getPath().equalsIgnoreCase(getPath());
		}
		return false;
	}

	public Block getBlock(int x, int y, int z){
		return null;
	}
	public void setBlock(Vector3 coords, Block block){

	}

	public IChunk[] getAllChunks(){
		return chunks;
	}
	public IChunk getChunk(int x, int z){
		return null;
	}
	public boolean isChunkLoaded(int chunkX, int chunkZ){
		return false;
	}
	public void setChunk(int x, int z, IChunk chunk){

	}

	public int getBlockID(int x, int y, int z){
		return 0;
	}
	public void setBlockID(int x, int y, int z, int blockID){

	}
	public int getBlockMeta(int x, int y, int z){
		return 0;
	}
	public void setBlockMeta(int x, int y, int z, int meta){

	}

	/**
	 * @return A 3-byte triad/int of format 0x{RR}{GG}{BB}
	 */
	public int getBlockColor(int x, int y, int z){
		return 0;
	}
	public void setBlockColor(int x, int y, int z, int r, int g, int b){

	}

	public double getGravityAt(Vector3 coords){
		return 9.8; // Earth gravitational constant; not sure if same for Minecraft ;)
	}

	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}

	public int getDefaultGamemode() {
		return defaultGamemode;
	}
	public void setDefaultGamemode(int gamemode) {
		defaultGamemode = gamemode;
	}

	public Vector3d getSpawnPos() {
		return spawnPos;
	}
	public void setSpawnpos(Vector3d spawnpos) {
		this.spawnPos = spawnpos;
	}

	public Entity[] getEntities(){
		return (Entity[]) entities.toArray();
	}
	public Entity[] getEntities(Vector3d center, double radius){
		return getEntities(center, radius, validateInstance);
	}
	public Entity[] getEntities(Vector3d center, double radius, EntityValidate v){
		List<Entity> ret = new ArrayList<Entity>();
		for(Entity ent: entities){
			if(ent.distance(center) <= radius){
				if(v.isValid(ent)){
					ret.add(ent);
				}
			}
		}
		return (Entity[]) ret.toArray();
	}
	public Entity getClosestEntity(Vector3d center){
		return getClosestEntity(center, validateInstance);
	}
	public Entity getClosestEntity(Vector3d center, EntityValidate v){
		Entity ret = null;
		double currentDelta = Double.MAX_VALUE;
		for(Entity ent: entities){
			double distance = ent.distance(center);
			if(distance < currentDelta){
				if(v.isValid(ent)){
					currentDelta = distance;
					ret = ent;
				}
			}
		}
		return ret;
	}

	public static interface EntityValidate{
		public boolean isValid(Entity ent);
	}

	public static class DummyValidate implements EntityValidate{
		public boolean isValid(Entity ent){
			return true;
		}
	}
}
