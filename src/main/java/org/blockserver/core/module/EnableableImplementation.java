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
package org.blockserver.core.module;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author BlockServer Team
 * @see org.blockserver.core.module.Enableable
 */
public abstract class EnableableImplementation extends Enableable {
    Map<EnableableImplementation, Boolean> instances = new WeakHashMap<>();

    public final void enable() {
        onEnable();
        instances.put(this, true);
    }

    @Override
    public boolean isEnabled() {
        return instances.getOrDefault(this, false);
    }

    public final void disable() {
        onDisable();
        instances.put(this, false);
    }
}
