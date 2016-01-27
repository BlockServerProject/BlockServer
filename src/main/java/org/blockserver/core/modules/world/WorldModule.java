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

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

/**
 * Written by Exerosis!
 *
 * @author BlockServer Team
 * @see org.blockserver.core.module.Module
 */
public class WorldModule extends Module {
    public WorldModule(Server server) {
        super(server);
    }

    /**
     * Setter method for block material.
     *
     * @param block    Block which's material will be set.
     * @param material material of the block
     */
    public void setBlockMaterial(Block block, Material material) {
        block.setMaterial(material);
    }


}