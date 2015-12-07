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
import lombok.Setter;
import org.blockserver.core.event.system.Cancellable;

import java.net.InetAddress;

/**
 * Represents a packet recieved or ready to be sent in byte form.
 *
 * @author BlockServer Team
 */
public class RawPacket implements Cancellable {
    @Getter @Setter private byte[] buffer;
    @Getter @Setter private InetAddress address;

    public RawPacket(byte[] buffer, InetAddress address) {
        this.buffer = buffer;
        this.address = address;
    }
}
