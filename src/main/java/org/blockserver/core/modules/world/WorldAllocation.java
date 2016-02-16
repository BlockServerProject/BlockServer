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

/**
 * Written by Exerosis!
 *
 * @author BlockServer Team
 * @see WorldServerModule
 */
public class WorldAllocation {
    private short[][][][] memoryAllocation;

    public WorldAllocation(short allocationSize) {
        int chunkCount = allocationSize / 131072 - allocationSize % 131072;
        memoryAllocation = new short[chunkCount][16][16][256];

        for (int i = 0; i < memoryAllocation.length; i++) {
            memoryAllocation[i] = null;
        }
    }


    public void setChunkAt(int id, short[][][]) {

    }

    public short[][][] getChunkAt(int chunkID) {
        return memoryAllocation[chunkID];
    }
}
