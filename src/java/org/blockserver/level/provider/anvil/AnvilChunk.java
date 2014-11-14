package org.blockserver.level.provider.anvil;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.blockserver.entity.SavedEntity;
import org.blockserver.io.nbt.CompoundTag;
import org.blockserver.io.nbt.IntTag;
import org.blockserver.io.nbt.LongTag;
import org.blockserver.io.nbt.NBTReader;
import org.blockserver.level.Level;
import org.blockserver.level.provider.IChunk;

public class AnvilChunk extends IChunk{
	private CompoundTag nbt;
	public AnvilChunk(NBTReader reader, Level level) throws IOException{
		nbt = (CompoundTag) reader.readTag();
	}

	@Override
	public Map<Integer, SavedEntity> getMappedEntities(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBlocks(){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getDamages(){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getSkyLights(){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getBlockLights(){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getBiomeIds(){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int[] getBiomeColors(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SavedEntity> getEntities(){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getTiles(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX(){
		return ((IntTag) nbt.get("xPos")).getValue();
	}
	
	@Override
	public int getZ(){
		return ((IntTag) nbt.get("zPos")).getValue();
	}

	protected void onUpdate(){
		((LongTag) nbt.get("LastUpdate")).setValue(System.currentTimeMillis());
	}
}
