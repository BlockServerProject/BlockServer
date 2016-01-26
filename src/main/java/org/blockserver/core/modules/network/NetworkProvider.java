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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A Network Provider is an interface capable of sending and receiving packets.
 * Each Protocol has it's own NetworkProvider.
 *
 * @author BlockServer Team
 * @see NetworkModule
 */
public abstract class NetworkProvider {

    @Getter private Server server;
    @Getter private NetworkConverter converter;

    private Queue<RawPacket> inQueue = new ConcurrentLinkedQueue<>();
    private Queue<RawPacket> outQueue = new ConcurrentLinkedQueue<>();

    public NetworkProvider(Server server, NetworkConverter converter) {
        this.server = server;
        this.converter = converter;
    }

    /**
     * This method is used by the {@linkplain NetworkProvider} superclass to send a packet
     * through the underlying layer.
     *
     * @param packet The packet to be sent.
     * @return If the packet has been sent successfully.
     */
    protected abstract boolean _sendPacket(RawPacket packet);

    /**
     * Sends a {@linkplain RawPacket}. This places it in the packet out queue.
     * If isImmediate, the packet will skip the packet out queue.
     *
     * @param packet The packet to be sent.
     * @param isImmediate If the packet should be sent immediately, thus skipping queues.
     * @return If the packet was sent successfully.
     */
    public final boolean sendPacket(RawPacket packet, boolean isImmediate) {
        if(isImmediate) {
            if(!_sendPacket(packet)) {
                return false;
            }
        } else outQueue.add(packet);
        return true;
    }

    /**
     * Used by the NetworkModule to get the latest packet received.
     * If no packet is in the queue, returns null.
     *
     * @return The latest packet received, null if the queue is empty.
     */
    protected RawPacket getNextPacket() {
        if(inQueue.isEmpty()) return null;
        return inQueue.remove();
    }
}
