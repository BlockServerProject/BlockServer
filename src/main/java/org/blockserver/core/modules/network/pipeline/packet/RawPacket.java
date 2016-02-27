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
package org.blockserver.core.modules.network.pipeline.packet;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.CancellableImplementation;

import java.net.InetSocketAddress;

/**
 * Represents a packet recieved or ready to be sent in byte form.
 *
 * @author BlockServer Team
 */
public class RawPacket implements CancellableImplementation {
    @Getter @Setter private BinaryBuffer buffer;
    @Getter @Setter private InetSocketAddress address;

    public RawPacket(BinaryBuffer buffer, InetSocketAddress address) {
        this.buffer = buffer;
        this.address = address;
    }
}
