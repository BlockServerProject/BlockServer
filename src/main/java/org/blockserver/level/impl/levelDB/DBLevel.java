package org.blockserver.level.impl.levelDB;

import org.blockserver.Server;
import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.LevelLoadException;
import org.blockserver.utils.Position;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;

/**
 * An implementation of a LevelDB level.
 */
public class DBLevel implements ILevel{
    private File lvlLocation;
    private File dbLocation;
    private Server server;
    private Position spawnPosition;

    private DB database;

    private IChunk spawnChunk;

    private boolean loaded = false;

    public DBLevel(File lvlLocation, Server server){
        this.lvlLocation = lvlLocation;
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

        } catch (IOException e) {
            throw new LevelLoadException(e.getMessage());
        } finally {
            try {
                database.close();
            } catch (IOException e) {
                throw new LevelLoadException("Failed to Close Database: "+e.getMessage());
            }
        }

        server.getLogger().info("Level loaded!");
    }

    @Override
    public IChunk getChunkAt(int x, int z) {
        return null;
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
