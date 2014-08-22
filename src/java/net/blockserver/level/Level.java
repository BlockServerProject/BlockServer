package net.blockserver.level;


import java.util.ArrayList;
import java.util.List;

import net.blockserver.blocks.Block;
import net.blockserver.entity.Entity;
import net.blockserver.level.format.IChunk;
import net.blockserver.level.format.ILevel;
import net.blockserver.math.Vector3;
import net.blockserver.math.Vector3d;

public class Level implements ILevel
{
    public final static EntityValidate validateInstance = new Level.DummyValidate();
    
    private IChunk[] chunks;
    private int seed;
    private int gamemode;
    private Vector3d spawnpos;
    private List<Entity> entities = new ArrayList<Entity>();

    public String getName() {
        return null;
    }

    public void setName() {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String p) {

    }

    public Block getBlock(int x, int y, int z) {
        return null;
    }

    public void setBlock(Block block) {

    }

    public IChunk[] getAllChunks() {
        return chunks;
    }

    public IChunk getChunk(int x, int z) {
        return null;
    }

    public int getBlockID(int x, int y, int z) {
        return 0;
    }

    public void setBlockID(int x, int y, int z, int blockID) {

    }

    public int getBlockMeta(int x, int y, int z) {
        return 0;
    }

    public void setBlockMeta(int x, int y, int z, int meta) {

    }

    public int getBlockColor(int x, int y, int z) {
        return 0;
    }

    public void setBlockColor(int x, int y, int z, int r, int g, int b) {

    }

    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return false;
    }

    public void setChunk(int x, int z) {

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

	public int getGamemode() {
		return gamemode;
	}

	public void setGamemode(int gamemode) {
		this.gamemode = gamemode;
	}

	public Vector3d getSpawnpos() {
		return spawnpos;
	}

	public void setSpawnpos(Vector3d spawnpos) {
		this.spawnpos = spawnpos;
	}

	public Entity[] getEntities(){
	    return entities.toArray(new Entity[entities.size()]);
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
	    return ret.toArray(new Entity[ret.size()]);
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
