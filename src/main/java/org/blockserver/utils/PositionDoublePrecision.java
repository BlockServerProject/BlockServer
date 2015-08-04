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
package org.blockserver.utils;

import org.blockserver.level.ChunkPosition;

public class PositionDoublePrecision{
	private double x;
	private double y;
	private double z;

	public PositionDoublePrecision(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}

	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setZ(double z){
		this.z = z;
	}

	public ChunkPosition getChunkPos(){
		return new ChunkPosition(((int) x) >> 4, ((int) z) >> 4);
	}
}
