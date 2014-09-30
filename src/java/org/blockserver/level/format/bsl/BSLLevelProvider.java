package org.blockserver.level.format.bsl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.level.format.ChunkPosition;
import org.blockserver.level.format.LevelProvider;
import org.blockserver.math.Vector3d;

public class BSLLevelProvider implements LevelProvider{
	private Server server;
	private File dir, chunksDir;
	private Map<ChunkPosition, BSLChunk> cachedChunks;

	public BSLLevelProvider(Server server, File file){
		this.server = server;
		dir = file;
		chunksDir = new File(file, "chunks");
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isChunkLoaded(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isChunkGenerated(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deleteChunk(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector3d getSpawn(){
		// TODO Auto-generated method stub
		return null;
	}

	public File toFile(ChunkPosition pos){
		return new File(chunksDir, String.format("%d,%d.bsc", pos.getX(), pos.getZ()));
	}
}
