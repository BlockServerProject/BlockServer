/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core.modules.world;

import org.blockserver.core.utilities.ByteUtil;

public class World {
    private ChunkPosition[][] loadedChunks;
    private WorldAllocation worldAllocation;

    public World(short allocationSize) {
        worldAllocation = new WorldAllocation(allocationSize);
    }

    public void saveChunk(int x, int y) {

    }

    public short[][][] getChunk(int x, int y) {
        return worldAllocation.getChunkAt(loadedChunks[x][y].getChunkID());
    }

    public short getBlock(int x, int y, int z) {
        short[][][] chunk = getChunk(x / 16, y / 16);
        return chunk[x % 16][y % 16][z];
    }

    public byte getBlockMaterial(int x, int y, int z) {
        return ByteUtil.fromShort(getBlock(x, y, z))[0];
    }

    public byte getBlockLightLevel(int x, int y, int z) {
        return ByteUtil.fromShort(getBlock(x, y, z))[1];
    }
}