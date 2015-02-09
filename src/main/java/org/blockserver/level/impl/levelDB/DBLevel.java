package org.blockserver.level.impl.levelDB;

import org.blockserver.Server;
import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.LevelLoadException;
import org.blockserver.level.LevelSaveException;
import org.blockserver.level.impl.Chunk;
import org.blockserver.utils.Position;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * An implementation of a LevelDB level.
 */
public class DBLevel implements ILevel{
    private File lvlLocation;
    private File dbLocation;
    private Server server;
    private Position spawnPosition;

    private DB database;

    private Chunk spawnChunk;
    private ArrayList<Chunk> loadedChunks = new ArrayList<>();

    private boolean loaded = false;

    public DBLevel(File lvlLocation, Server server, Position spawnPosition){
        this.lvlLocation = lvlLocation;
        this.server = server;
        this.spawnPosition = spawnPosition;
        this.dbLocation = new File(lvlLocation.getAbsolutePath() + File.separator + "db");
        if(!lvlLocation.exists() || !lvlLocation.isDirectory()){
            server.getLogger().warning("Could not find LevelDB location... Creating new world.");
            try {
                newWorld();
            } catch (IOException e) {
                server.getLogger().fatal("FAILED TO CREATE NEW LEVEL!");
                server.getLogger().trace("IOException: " + e.getMessage());
                e.printStackTrace();

                server.stop();
            }
        }
        try {
            loadLevel();
        } catch (LevelLoadException e) {
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
        options.compressionType(CompressionType.SNAPPY);
        options.createIfMissing(true);

        try {
            database = factory.open(dbLocation, options);
            server.getLogger().info("Loading spawn chunk...");
            spawnChunk = new DBChunk(spawnPosition, database);
            spawnChunk.load();
            server.getLogger().info("Spawn chunk loaded!");
        } catch (IOException e) {
            throw new LevelLoadException(e.getMessage());
        }

        server.getLogger().info("Level loaded!");
    }

    @Override
    public void saveLevel() throws LevelSaveException{
        spawnChunk = null;
        server.getLogger().info("Saving Level...");
        for(Chunk chunk: loadedChunks){
            chunk.save();
        }

        try {
            database.close();
            server.getLogger().info("Level Saved!");
        } catch (IOException e) {
            throw new LevelSaveException(e.getMessage());
        }
    }

    @Override
    public synchronized IChunk getChunkAt(int x, int z) {
        for(Chunk chunk: loadedChunks){
            if(chunk.getPosition().getX() == x && chunk.getPosition().getZ() == z){
                return chunk;
            }
        }
        DBChunk chunk = new DBChunk(new Position(x, 0, z), database);
        chunk.load();
        loadedChunks.add(chunk);
        return chunk;
    }

    public synchronized boolean unloadChunk(int x, int z){
        for(Chunk chunk: loadedChunks){
            if(chunk.getPosition().getX() == x && chunk.getPosition().getZ() == z){
                loadedChunks.remove(chunk);
                return true;
            }
        }
        return false;
    }

    @Override
    public Position getSpawnPosition() {
        return spawnPosition;
    }

    @Override
    public void setSpawnPosition(Position position) {
        this.spawnPosition = position;
    }

    private void newWorld() throws IOException {
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
