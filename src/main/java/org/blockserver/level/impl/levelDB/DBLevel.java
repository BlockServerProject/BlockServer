package org.blockserver.level.impl.levelDB;

import org.blockserver.Server;
import org.blockserver.level.ChunkPosition;
import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.LevelLoadException;
import org.blockserver.level.LevelSaveException;
import org.blockserver.level.impl.Chunk;
import org.blockserver.utils.PositionDoublePrecision;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * An implementation of a LevelDB level.
 */
public class DBLevel extends ILevel{
	private File lvlLocation;
	private File dbLocation;
	private Server server;
	private PositionDoublePrecision spawnPosition;

	private DB database;

	private Chunk spawnChunk;
	private final HashMap<ChunkPosition, Chunk> loadedChunks = new HashMap<>();

	private boolean loaded = false;

	public DBLevel(File lvlLocation, Server server, PositionDoublePrecision spawnPosition){
		this.lvlLocation = lvlLocation;
		this.server = server;
		this.spawnPosition = spawnPosition;
		dbLocation = new File(lvlLocation.getAbsolutePath() + File.separator + "db");
		if(!lvlLocation.exists() || !lvlLocation.isDirectory()){
			server.getLogger().warning("Could not find LevelDB location... Creating new world.");
			try{
				newWorld();
			}catch(IOException e){
				server.getLogger().fatal("Failed to create new level!");
				server.getLogger().trace("IOException: " + e.getMessage());
				e.printStackTrace();

				server.stop();
			}
		}
		try{
			loadLevel();
		}catch(LevelLoadException e){
			server.getLogger().fatal("FAILED TO LOAD LEVEL!");
			server.getLogger().trace("LevelLoadException: "+e.getMessage());
			e.printStackTrace();

			server.stop();
		}
	}

	@Override
	public void loadLevel() throws LevelLoadException{
		server.getLogger().info("Loading Level... (LVL TYPE: LevelDB)");

		DBFactory factory = new Iq80DBFactory();

		Options options = new Options();
		options.compressionType(CompressionType.ZLIB);
		options.createIfMissing(true);

		try{
			database = factory.open(dbLocation, options);
			server.getLogger().info("Loading spawn chunk...");
			spawnChunk = new DBChunk(spawnPosition.getChunkPos(), database);
			spawnChunk.load();
			server.getLogger().info("Spawn chunk loaded!");
		}catch(IOException e){
			throw new LevelLoadException(e.getMessage());
		}

		server.getLogger().info("Level loaded!");
	}

	@Override
	public void saveLevel() throws LevelSaveException{
		spawnChunk = null;
		server.getLogger().info("Saving Level...");
		Collection<Chunk> values;
		synchronized(loadedChunks){
			values = loadedChunks.values();
		}
		values.forEach(Chunk::save);

		try{
			database.close();
			server.getLogger().info("Level Saved!");
		}catch(IOException e){
			throw new LevelSaveException(e.getMessage());
		}
	}

	@Override
	public synchronized IChunk getChunkAt(ChunkPosition pos){
		Chunk get = loadedChunks.get(pos);
		if(get != null){
			return get;
		}
		DBChunk chunk = new DBChunk(pos, database);
		chunk.load();
		synchronized(loadedChunks){
			loadedChunks.put(pos, chunk);
		}
		return chunk;
	}

	@Override
	public synchronized boolean unloadChunk(ChunkPosition pos){
		synchronized(loadedChunks){
			Chunk chunk = loadedChunks.remove(pos);
			if(chunk == null){
				return false;
			}
			chunk.save();
			return true;
		}
	}

	public boolean isChunkLoaded(ChunkPosition pos){
		return loadedChunks.containsKey(pos);
	}

	@Override
	public PositionDoublePrecision getSpawnPosition(){
		return spawnPosition;
	}

	@Override
	public void setSpawnPosition(PositionDoublePrecision position){
		spawnPosition = position;

	}

	private void newWorld() throws IOException{
		lvlLocation.mkdirs();
		dbLocation.mkdirs();

		DBFactory factory = new Iq80DBFactory();

		Options options = new Options();
		options.compressionType(CompressionType.SNAPPY);
		options.createIfMissing(true);

		database = factory.open(dbLocation,  options);

		//TODO: Generate Spawn Chunks

		database.close();
		server.getLogger().info("World Created!");
	}
}
