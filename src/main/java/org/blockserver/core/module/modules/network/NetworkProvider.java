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
package org.blockserver.core.module.modules.network;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Written by Exerosis!
 */
public abstract class NetworkProvider extends Module {
    @Getter private final Set<RawPacket> packetQueue = new HashSet<>();

    public NetworkProvider(Server server) {
        super(server);
    }

    protected void queuePacket(RawPacket packet) {
        packetQueue.add(packet);
    }

    public Collection<RawPacket> receivePackets() {
        Set<RawPacket> messages = new HashSet<>(packetQueue);
        packetQueue.clear();
        return messages;
    }

    public abstract void sendPacket(RawPacket packet);
}
