package org.blockserver.level.format;

import java.util.Collection;
import java.util.Map;

import org.blockserver.entity.SavedEntity;

public interface IChunk{
	public Map<Integer, SavedEntity> getMappedEntities();
	public Collection<SavedEntity> getEntities();
	public IMiniChunk[] getMiniChunks();
}
