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
package org.blockserver.core.modules.entity;

import org.blockserver.core.modules.world.positions.Location;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Written by Exerosis!
 *
 * @author BlockServer Team
 */
public class Entity {
    private final Map<Class<? extends EntityModule>, EntityModule> modules = new HashMap<>();
    private float x;
    private float y;
    private float z;

    //TODO deal with locations and what not!!
    public Entity(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        modules.values().forEach(EntityModule::enable);
    }

    public Entity(Location location) {
        this(location.getX(), location.getY(), location.getZ());
    }


    public void addModule(EntityModule module) {
        modules.put(module.getClass(), module);
    }

    public void removeModule(Class<? extends EntityModule> moduleClass) {
        modules.remove(moduleClass);
    }

    public void removeModule(EntityModule module) {
        removeModule(module.getClass());
    }

    public EntityModule getModule(Class<? extends EntityModule> moduleClass) {
        return modules.get(moduleClass);
    }

    public void destroy() {
        modules.values().forEach(EntityModule::disable);
    }


    public Map<Class<? extends EntityModule>, EntityModule> getModules() {
        return Collections.unmodifiableMap(modules);
    }

    public Location getLocation() {
        return new Location(x, y, z);
    }
}