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
import org.blockserver.core.modules.network.message.Message;

import java.net.SocketAddress;

public abstract class NetworkAdapter {
    @Getter private NetworkProvider provider; //Set in constructor

    public void sendMessage(Message message, SocketAddress sendTo) {
        RawPacket packet = messageToPacket(message);
        packet.setAddress(sendTo);
        provider.sendPacket(packet);
    }

    protected abstract Message packetToMessage(RawPacket packet);

    protected abstract RawPacket messageToPacket(Message message);
}