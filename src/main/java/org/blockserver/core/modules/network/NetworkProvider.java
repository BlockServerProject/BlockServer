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

import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Abstract class, represents a provider that handles low-level
 * networking (TCP, UDP, RakNet, etc.)
 *
 * @author BlockServer Team
 */
public abstract class NetworkProvider {
    @Getter private NetworkAdapter adapter;
    private Queue<RawPacket> packetQueue = new ConcurrentLinkedQueue<>();

    public NetworkProvider(NetworkAdapter adapter) {
        this.adapter = adapter;
    }

    protected abstract void init(SocketAddress address);

    protected final RawPacket getNextPacket() {
        if(!packetQueue.isEmpty()) {
            return packetQueue.remove();
        }
        return null;
    }

    /**
     * Used by implementing class.
     * @param packet Packet to be added.
     */
    protected final void addPacketToQueue(RawPacket packet) {
        packetQueue.add(packet);
    }

    /**
     * Sends a packet to the specified address in the packet.
     * @param packet The RawPacket instance with the buffer and address.
     * @return If the packet was sent successfully.
     */
    public abstract boolean sendPacket(RawPacket packet);
}
