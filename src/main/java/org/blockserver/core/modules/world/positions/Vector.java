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
 * @see WorldServerModule
 */
public class Vector {
    @Getter float x;
    @Getter float y;
    @Getter float z;

    public Vector(Vector vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}