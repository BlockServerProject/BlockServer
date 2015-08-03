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
package org.blockserver.level;

public class ChunkPosition{
	private int x;
	private int z;

	public ChunkPosition(int x, int z){
		this.x = x;
		this.z = z;
	}

	public int getX(){
		return x;
	}
	public int getZ(){
		return z;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ChunkPosition)){
			return false;
		}
		ChunkPosition other = (ChunkPosition) obj;
		return other.getX() == getX() && other.getZ() == getZ();
	}
}
