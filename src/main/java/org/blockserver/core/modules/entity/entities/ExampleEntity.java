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
package org.blockserver.core.modules.entity.entities;

import org.blockserver.core.modules.entity.Entity;
import org.blockserver.core.modules.entity.modules.ExampleEntityModule;
import org.blockserver.core.modules.world.positions.Location;

/**
 * Created by Exerosis.
 */
public class ExampleEntity extends Entity {
    public ExampleEntity(Location location) {
        super(location);
    }

    public ExampleEntity(float x, float y, float z) {
        super(x, y, z);
        addModule(new ExampleEntityModule(this));
    }
}
