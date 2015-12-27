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



import org.blockserver.core.message.Message;

import java.util.Collection;

/**
 * Written by Exerosis!
 */
public interface NetworkConverter {
    Collection<RawPacket> toPackets(Collection<Message> messages);

    RawPacket toPacket(Message message);

    Collection<Message> toMessages(Collection<RawPacket> packets);

    Message toMessage(RawPacket packet);
}