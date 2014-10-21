package org.blockserver.level.provider.bsl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.EntityType;
import org.blockserver.entity.SavedEntity;
import org.blockserver.io.bsf.BSF;
import org.blockserver.io.bsf.BSFReader;
import org.blockserver.io.bsf.BSFWriter;
import org.blockserver.level.generator.Generator;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.IChunk;

//TODO getBlocks, getDamages, getSkyLights, getBlockLights without ByteBuffer!
public class BSLChunk extends IChunk{
	private Server server;
	private File file;
	private ChunkPosition pos;
	private BSLLevelProvider provider;
	private Map<Integer, SavedEntity> entities = new HashMap<Integer, SavedEntity>();
	private byte[] blockIds;
	private byte[] blockDamages;
	private byte[] skyLights;
	private byte[] blockLights;
	private byte[] biomeIds;
	private int[] biomeColors = new int[0x100];

	public BSLChunk(Server server, File chunkFile, ChunkPosition pos, BSLLevelProvider provider, int reason) throws IOException{
		this.server = server;
		file = chunkFile;
		this.pos = pos;
		this.provider = provider;
		load(reason);
	}

	private void load(int flag) throws IOException{
		if(!file.exists()){
			generate(flag);
			return;
		}
		FileInputStream is = new FileInputStream(file);
		BSFReader reader = new BSFReader(is);
		// blocks
		blockIds = reader.read(0x1000);
		blockDamages = reader.read(0x800);
		skyLights = reader.read(0x800);
		blockLights = reader.read(0x800);
		biomeIds = reader.read(0x100);
		for(int i = 0; i < 0x100; i++){
			biomeColors[i] = reader.readInt();
		}
		// TODO read tiles
		int size = reader.readInt();
		entities = new HashMap<Integer, SavedEntity>(size);
		for(int i = 0; i < size; i++){
			Byte typeID = reader.readByte();
			EntityType<?> type = server.getEntityTypeMgr().getType(typeID);
			if(type == null){
				server.getLogger().warning("Unknown entity type ID %d found at %s; this entity will be ignored.", type, file.getCanonicalPath());
			}
			else{
				SavedEntity ent = type.read(reader, server);
				entities.put(ent.getEID(), ent);
			}
		}
		reader.close();
	}
	
	//TODO Linking to Flat Generator
	//TODO Handle biome Content
	private void generate(int flag) throws IOException {
		// TODO use generators
		Generator generator = provider.getGenerator();
		generator.generateChunk(pos, flag);
		save();
	}
	
	public void save() throws IOException{
		BSFWriter writer = new BSFWriter(new FileOutputStream(file), BSF.Type.LEVEL_INDEX);
		// blocks
		writer.write(blockIds);
		writer.write(blockDamages);
		writer.write(skyLights);
		writer.write(blockLights);
		writer.write(biomeIds);
		for(int color: biomeColors){
			writer.writeInt(color);
		}
		// entities
		writer.writeInt(entities.size());
		// tiles
		// TODO write tiles
		for(SavedEntity entity: entities.values()){
			writer.writeByte(entity.getTypeID());
			EntityType<? extends SavedEntity> type = entity.getType();
			type.write(writer, entity);
		}
		writer.flush();
		writer.close();
	}

	@Override
	public Map<Integer, SavedEntity> getMappedEntities(){
		return entities;
	}
	@Override
	public Collection<SavedEntity> getEntities(){
		return entities.values();
	}

	@Override
	public byte[] getBlocks(){
		return blockIds;
	}
	@Override
	public byte[] getDamages(){
		return blockDamages;
	}
	@Override
	public byte[] getSkyLights(){
		return skyLights;
	}
	@Override
	public byte[] getBlockLights(){
		return blockLights;
	}
	@Override
	public byte[] getBiomeIds(){
		return biomeIds;
	}
	@Override
	public int[] getBiomeColors(){
		return biomeColors;
	}
	@Override
	public byte[] getTiles(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX(){
		return pos.getX();
	}
	@Override
	public int getZ(){
		return pos.getZ();
	}
}
