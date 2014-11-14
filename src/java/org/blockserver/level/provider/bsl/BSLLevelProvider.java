package org.blockserver.level.provider.bsl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.blockserver.Server;
import org.blockserver.io.bsf.BSF;
import org.blockserver.io.bsf.BSFReader;
import org.blockserver.io.bsf.BSFWriter;
import org.blockserver.level.generator.Generator;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.LevelCorruptedException;
import org.blockserver.level.provider.LevelProvider;
import org.blockserver.math.Vector3d;
import org.blockserver.utility.Gettable;

public class BSLLevelProvider extends LevelProvider{
	public class GeneratorGetter implements Gettable<Generator>{
		private Map<String, Object> data;
		public GeneratorGetter(Map<String, Object> data){
			this.data = data;
		}

		@Override
		public Generator get(){
			long seed = (long) data.get(BSF.LI_SEED);
			try{
				return server.getGeneratorMgr().generate((String) data.get(BSF.LI_GENERATOR),
						BSLLevelProvider.this, seed, new Random(seed), 0,
						(String) data.get(BSF.LI_GENERATION_OPTS));
			}
			catch(Throwable e){
				e.printStackTrace();
				return null;
			}
		}
	}

	private Server server;
	private File dir, chunksDir;
	private long seed;
	private Vector3d spawnPos;
	private Gettable<Generator> generator;
	private Map<ChunkPosition, BSLChunk> cachedChunks = new HashMap<ChunkPosition, BSLChunk>();

	public BSLLevelProvider(Server server, File file, String name, long seed){
		super(name);
		this.server = server;
		dir = file;
		chunksDir = new File(file, "chunks");
		chunksDir.mkdirs();
		this.seed = seed;
	}

	@Override
	public void init(Gettable<Generator> generator) throws LevelCorruptedException{
		File index = new File(dir, "index.bsf");
		if(dir.isDirectory() && index.isFile()){
			try{
				BSFReader reader = new BSFReader(new FileInputStream(index));
				try{
					Map<String, Object> data = reader.readAll();
					reader.close();
					if(!reader.getType().equals(BSF.Type.LEVEL_INDEX)){
						throw new BSF.InvalidBSFFileException("BSF level index corrupted: incorrect type!");
					}
					spawnPos = new Vector3d(
							(double) data.get(BSF.LI_SPAWN_X),
							(double) data.get(BSF.LI_SPAWN_Y),
							(double) data.get(BSF.LI_SPAWN_Z));
					seed = (long) data.get(BSF.LI_SEED);
					generator = new GeneratorGetter(data);
				}
				catch(BSF.InvalidBSFFileException | NullPointerException e){
					try{
						reader.close();
					}
					catch(Exception ex){}
					throw new LevelCorruptedException(true, e, this);
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			if(!dir.mkdirs()){
				throw new RuntimeException("Unable to make world directories");
			}
			Map<String, Object> data = new HashMap<String, Object>(6);
			data.put(BSF.LI_SPAWN_X, 0d);
			data.put(BSF.LI_SPAWN_Y, 127d);
			data.put(BSF.LI_SPAWN_Z, 0d);
			data.put(BSF.LI_SEED, seed);
			firstInit();
		}
	}
	public void firstInit(){
		try{
			BSFWriter writer = new BSFWriter(new FileOutputStream(
					new File(dir, "index.bsf")), BSF.Type.LEVEL_INDEX);
			Map<String, Object> args = new HashMap<String, Object>(5);
			args.put(BSF.LI_SPAWN_X, 0d);
			args.put(BSF.LI_SPAWN_Y, 127d);
			args.put(BSF.LI_SPAWN_Z, 0d);
			args.put(BSF.LI_SEED, generator.get().getSeed());
			args.put(BSF.LI_GENERATOR, generator.get().getClass().getCanonicalName());
			args.put(BSF.LI_GENERATION_OPTS, generator.get().getArgs());
			writer.writeAll(args);
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean loadChunk(ChunkPosition pos, int reason){
		if(cachedChunks.containsKey(pos)){
			return false;
		}
		try{
			cachedChunks.put(pos, new BSLChunk(server, toFile(pos), pos, this, reason));
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
		return spawnPos;
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

	public Generator getGenerator(){
		return generator.get();
	}
}
