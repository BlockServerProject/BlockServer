package org.blockserver.level.format.bsl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.EntityType;
import org.blockserver.entity.SavedEntity;
import org.blockserver.io.bsf.BSFReader;
import org.blockserver.level.format.IChunk;
import org.blockserver.level.format.IMiniChunk;

public class BSLChunk implements IChunk{
	private Server server;
	private File file;
	private Map<Integer, SavedEntity> entities;
	private BSLMiniChunk[] miniChunks = new BSLMiniChunk[8];

	public BSLChunk(Server server, File chunkFile) throws IOException{
		this.server = server;
		file = chunkFile;
		load();
	}

	private void load() throws IOException{
		FileInputStream is = new FileInputStream(file);
		BSFReader reader = new BSFReader(is);
		// blocks
		for(int Y = 0; Y < 8; Y++){
			byte[] ids = reader.read(0x1000);
			byte[] damages = reader.read(0x800);
			byte[] blockLights = reader.read(0x800);
			byte[] skyLights = reader.read(0x800);
			miniChunks[Y] = new BSLMiniChunk(ids, damages, blockLights, skyLights);
		}
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

	@Override
	public Map<Integer, SavedEntity> getMappedEntities(){
		return entities;
	}
	@Override
	public Collection<SavedEntity> getEntities(){
		return entities.values();
	}
	@Override
	public IMiniChunk[] getMiniChunks(){
		// TODO Auto-generated method stub
		return null;
	}
}
