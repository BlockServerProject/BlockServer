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
package org.blockserver.core.modules.network;


import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NetworkHandler extends Module {
    @Getter private final Set<Dispatcher> dispatchers = Collections.synchronizedSet(new HashSet<>());

    public NetworkHandler(Server server) {
        super(server);
    }

    public void provide(RawPacket packet) {
        for (Dispatcher dispatcher : dispatchers) {
            dispatcher.dispatch(packet);
        }

    }

    public void unregisterDispatcher(Dispatcher dispatcher) {
        dispatchers.remove(dispatcher);
    }

    public void registerDispatcher(Dispatcher dispatcher) {
        dispatchers.add(dispatcher);
    }
}