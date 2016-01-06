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

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Written by Exerosis!
 */
public class NetworkProvider extends Module {
    private final BlockingQueue<RawPacket> packetOutQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<RawPacket> packetInQueue = new LinkedBlockingQueue<>();

    public NetworkProvider(Server server) {
        super(server);
    }

    public void queueOutboundPackets(RawPacket... packets) {
        packetOutQueue.addAll(packets.length > 1 ? Arrays.asList(packets) : Collections.singletonList(packets[0]));
    }

    public void queueInboundPackets(RawPacket... packets) {
        if (packets.length > 0)
            getServer().getExecutorService().execute(() -> Collections.addAll(packetInQueue, packets));
    }

    public Collection<RawPacket> receiveInboundPackets() {
        Collection<RawPacket> messages = new HashSet<>();
        packetInQueue.drainTo(messages);
        return messages;
    }

    public Collection<RawPacket> receiveOutboundPackets() {
        Collection<RawPacket> packets = new HashSet<>();
        packetOutQueue.drainTo(packets);
        return packets;
    }

    public Collection<RawPacket> getPacketInQueue() {
        return new HashSet<>(packetInQueue);
    }

    public Collection<RawPacket> getPacketOutQueue() {
        return new HashSet<>(packetOutQueue);
    }
}