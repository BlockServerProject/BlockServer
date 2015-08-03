/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.level.impl.dummy;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.IChunk;
import org.blockserver.level.ILevel;
import org.blockserver.level.LevelSaveException;
import org.blockserver.utils.PositionDoublePrecision;

/**
 * A Dummy implementation of a Level.
 */
public class DummyLevel extends ILevel{

	private PositionDoublePrecision spawnPosition;

	public DummyLevel(PositionDoublePrecision spawnPosition){
		this.spawnPosition = spawnPosition;
	}

	@Override
	public void loadLevel(){}

	@Override
	public void saveLevel() throws LevelSaveException{}

	@Override
	public IChunk getChunkAt(ChunkPosition pos){
		IChunk chunk = new DummyChunk(pos);
		chunk.generate();
		return chunk;
	}

	@Override
	public boolean unloadChunk(ChunkPosition pos){
		return true;
	}

	@Override
	public boolean isChunkLoaded(ChunkPosition pos){
		return true;
	}

	@Override
	public PositionDoublePrecision getSpawnPosition(){
		return spawnPosition;
	}

	@Override
	public void setSpawnPosition(PositionDoublePrecision position){
		spawnPosition = position;
	}
}
