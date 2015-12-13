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
package org.blockserver.core.module.modules.player;

import org.blockserver.core.Server;
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.module.Module;

/**
 * Module that handles players.
 */
public class PlayerModule extends Module {
    public PlayerModule(Server server) {
        super(server);
    }


    @Override
    public void onEnable() {
        super.onEnable();

        new ServerEventListener<>()
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}