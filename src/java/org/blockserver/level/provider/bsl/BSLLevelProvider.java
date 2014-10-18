package org.blockserver.level.provider.bsl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.LevelProvider;
import org.blockserver.math.Vector3d;

public class BSLLevelProvider extends LevelProvider{
	private Server server;
	private File dir, chunksDir;
	private Map<ChunkPosition, BSLChunk> cachedChunks = new HashMap<ChunkPosition, BSLChunk>();

	public BSLLevelProvider(Server server, File file, String name){
		super(name);
		this.server = server;
		dir = file;
		chunksDir = new File(file, "chunks");
		chunksDir.mkdirs();
	}

	@Override
	public void init(){
		if(dir.isDirectory()){
			@SuppressWarnings("unused")
			File index = new File(dir, "index.bsf");
			// TODO BSF LEVEL_INDEX reading
		}
		else{
			if(!dir.mkdirs()){
				throw new RuntimeException("Unable to make world directories");
			}
			@SuppressWarnings("unused")
			File index = new File(dir, "index.bsf");
			// TODO BSF LEVEL_INDEX loading
		}
	}

	@Override
	public boolean loadChunk(ChunkPosition pos){
		if(cachedChunks.containsKey(pos)){
			return false;
		}
		try{
			cachedChunks.put(pos, new BSLChunk(server, toFile(pos), pos));
			return true;
		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean saveChunk(ChunkPosition pos){
		if( !cachedChunks.containsKey(pos) ) return false;
		try{
			cachedChunks.get(pos).save();
		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean isChunkLoaded(ChunkPosition pos){
		return cachedChunks.containsKey(pos);
	}
	@Override
	public boolean isChunkGenerated(ChunkPosition pos){
		return toFile(pos).exists();
	}
	@Override
	public boolean deleteChunk(ChunkPosition pos){
		toFile(pos).delete();
		return true;
	}

	@Override
	public Vector3d getSpawn(){
		//TODO Custom Spawn point
		return new Vector3d(128, 4, 128);
	}

	@Override
	public Server getServer(){
		return server;
	}

	public File toFile(ChunkPosition pos){
		return new File(chunksDir, String.format("%d_%d.bsc", pos.getX(), pos.getZ()));
	}

	@Override
	public BSLChunk getChunk(ChunkPosition pos){
		return cachedChunks.get(pos);
	}
}
