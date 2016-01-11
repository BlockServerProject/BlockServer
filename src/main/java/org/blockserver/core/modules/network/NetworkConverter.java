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


import org.blockserver.core.modules.message.Message;

/**
 * Written by Exerosis!
 */
public interface NetworkConverter {
    RawPacket toPacket(Message message);

    //As much as the player part might be helpful, I looked into it more and it will never actually be needed, also it makes for some huge problems. Sorry Jython buddy x)
    Message toMessage(RawPacket packet);
}