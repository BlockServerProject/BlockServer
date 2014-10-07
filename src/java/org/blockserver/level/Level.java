package org.blockserver.level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.blockserver.Server;
import org.blockserver.blocks.Block;
import org.blockserver.entity.Entity;
import org.blockserver.level.format.ChunkPosition;
import org.blockserver.level.format.LevelProvider;
import org.blockserver.level.format.LevelProviderType;
import org.blockserver.math.Vector3;
import org.blockserver.math.Vector3d;

public class Level{
	public final static EntityValidate validateInstance = new Level.DummyValidate();

	private String name;
	private File worldDir;
	private long seed;
	private int defaultGamemode;
	private Vector3d spawnPos;
	private List<Entity> loadedEntities = new ArrayList<Entity>(0);
	private LevelProvider provider;
	private Server server;

	public Level(String name, long seed, int defaultGamemode, Vector3d spawnPos, LevelProviderType<?> providerType, Server server){
		this(name, seed, defaultGamemode, spawnPos, providerType, server, server.getWorldsDir());
	}
	public Level(String name, long seed, int defaultGamemode, Vector3d spawnPos, LevelProviderType<?> providerType, Server server, File worldsDir){
		this.name = name;
		worldDir = new File(worldsDir, name);
		worldDir.mkdirs();
		this.seed = seed;
		this.defaultGamemode = defaultGamemode;
		this.spawnPos = spawnPos;
		provider = providerType.instantiate(server, worldDir);
		this.server = server;
		initialize();
	}
	public Level(LevelProvider provider){
		this.provider = provider;
		initialize();
	}
	private void initialize(){
		provider.init();
		new Thread(new Runnable(){
			@Override
			public void run(){
				Vector3d spawn = provider.getSpawn();
				ChunkPosition chunk = ChunkPosition.fromCoords(spawn.getX(), spawn.getZ());
				load(chunk);
			}
			private void load(ChunkPosition chunk){
				if(!provider.isChunkLoaded(chunk)){
					provider.loadChunk(chunk);
				}
			}
		}).start();
	}

	public String getName(){
		return name;
	}

	public Block getBlock(int x, int y, int z){
		return null;
	}
	public void setBlock(Vector3 coords, Block block){

	}

	public double getGravityAt(Vector3 coords){
		return 9.8; // Earth gravitational constant; not sure if same for Minecraft ;)
	}

	public long getSeed() {
		return seed;
	}
	public void setSeed(long seed) {
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
	public void setSpawnPos(Vector3d spawnPos) {
		this.spawnPos = spawnPos;
	}

	public LevelProvider getLevelProvider(){
		return provider;
	}
	public Server getServer(){
		return server;
	}

	public Entity[] getEntities(){
		return (Entity[]) loadedEntities.toArray();
	}
	public Entity[] getEntities(Vector3d center, double radius){
		return getEntities(center, radius, validateInstance);
	}
	public Entity[] getEntities(Vector3d center, double radius, EntityValidate v){
		List<Entity> ret = new ArrayList<Entity>();
		for(Entity ent: loadedEntities){
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
		for(Entity ent: loadedEntities){
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
