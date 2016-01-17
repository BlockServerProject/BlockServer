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

import lombok.Getter;
import org.blockserver.core.Server;

/**
 * Base class for all modules. New modules should implement this class.
 *
 * @author BlockServer Team
 * @see org.blockserver.core.modules
 * @see org.blockserver.core.module.EnableableImplementation
 */
public class Module implements EnableableImplementation {
    @Getter private final Server server;

    public Module(Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}