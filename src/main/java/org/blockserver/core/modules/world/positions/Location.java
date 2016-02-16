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
package org.blockserver.core.modules.world.positions;

import lombok.Getter;
import org.blockserver.core.modules.world.WorldServerModule;

/**
 * Written by Exerosis!
 *
 * @author BlockServer Team
 * @see org.blockserver.core.modules.world.positions.Vector
 * @see WorldServerModule
 */
public class Location extends Vector {
    @Getter long yaw;
    @Getter long pitch;

    public Location(Vector vector) {
        super(vector);
    }

    public Location(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Location(float x, float y, float z) {
        super(x, y, z);
    }

    public Location(float x, float y, float z, long yaw, long pitch) {
        this(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }
}