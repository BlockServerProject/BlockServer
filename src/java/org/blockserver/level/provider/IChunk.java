package org.blockserver.level.provider;

import java.util.Collection;
import java.util.Map;

import org.blockserver.GeneralConstants;
import org.blockserver.entity.SavedEntity;

public interface IChunk extends GeneralConstants{
	public Map<Integer, SavedEntity> getMappedEntities();
	public Collection<SavedEntity> getEntities();
	public byte[] getBlocks();
	public byte[] getDamages();
	public byte[] getSkyLights();
	public byte[] getBlockLights();
	public int getX();
	public int getZ();
	public byte[] getTiles();
}
