package org.blockserver.level.provider.bsl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.EntityType;
import org.blockserver.entity.SavedEntity;
import org.blockserver.io.bsf.BSFReader;
import org.blockserver.io.bsf.BSFWriter;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.IChunk;

//TODO getBlocks, getDamages, getSkyLights, getBlockLights without ByteBuffer!
public class BSLChunk implements IChunk{
	private Server server;
	private File file;
	private ChunkPosition pos;
	private Map<Integer, SavedEntity> entities;
	private BSLMiniChunk[] minichunks = new BSLMiniChunk[WORLD_MINICHUNK_CNT];

	public BSLChunk(Server server, File chunkFile, ChunkPosition pos) throws IOException{
		this.server = server;
		file = chunkFile;
		this.pos = pos;
		load();
	}

	private void load() throws IOException{
		FileInputStream is = new FileInputStream(file);
		BSFReader reader = new BSFReader(is);
		// blocks
		for(byte Y = 0; Y < WORLD_MINICHUNK_CNT; Y++){
			byte[] ids = reader.read(0x1000);
			byte[] damages = reader.read(0x800);
			byte[] blockLights = reader.read(0x800);
			byte[] skyLights = reader.read(0x800);
			byte[] biomes = reader.read(0x100);
			byte[] biomeColors = reader.read(0x400);
			minichunks[Y] = new BSLMiniChunk(pos.getMiniChunkPos(Y), ids, damages, blockLights, skyLights, biomes, biomeColors);
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
	public void save() throws IOException{
		BSFWriter writer = new BSFWriter(new FileOutputStream(file), null); // passing null won't trigger NullPointerException unless I call writeAll().
		for(byte Y = 0; Y < WORLD_MINICHUNK_CNT; Y++){
			writer.write(minichunks[Y].getBlocks());
			writer.write(minichunks[Y].getDamages());
			writer.write(minichunks[Y].getSkyLights());
			writer.write(minichunks[Y].getBlockLights());
			writer.write(minichunks[Y].getBiomes());
			writer.write(minichunks[Y].getBiomeColors());
		}
		// TODO write tiles
		for(SavedEntity entity: entities.values()){
			writer.writeByte(entity.getTypeID());
			EntityType<? extends SavedEntity> type = entity.getType();
			type.write(writer, entity);
		}
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
		ByteBuffer bb = ByteBuffer.allocate(0x8000);
		for(BSLMiniChunk mc: minichunks){
			bb.put(mc.getBlocks());
		}
		return bb.array();
	}
	@Override
	public byte[] getDamages(){
		ByteBuffer bb = ByteBuffer.allocate(0x4000);
		for(BSLMiniChunk mc: minichunks){
			bb.put(mc.getDamages());
		}
		return bb.array();
	}
	@Override
	public byte[] getSkyLights(){
		ByteBuffer bb = ByteBuffer.allocate(0x4000);
		for(BSLMiniChunk mc: minichunks){
			bb.put(mc.getSkyLights());
		}
		return bb.array();
	}
	@Override
	public byte[] getBlockLights(){
		ByteBuffer bb = ByteBuffer.allocate(0x4000);
		for(BSLMiniChunk mc: minichunks){
			bb.put(mc.getBlockLights());
		}
		return bb.array();
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
