/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.net.bridge.raknet;

/**
 * Represents a NetworkChannel.
 */
public enum NetworkChannel {
    CHANNEL_NONE((byte) 0),
    CHANNEL_PRIORITY((byte) 1),
    CHANNEL_WORLD_CHUNKS((byte) 2),
    CHANNEL_MOVEMENT((byte) 3),
    CHANNEL_BLOCKS((byte) 4),
    CHANNEL_WORLD_EVENTS((byte) 5),
    CHANNEL_ENTITY_SPAWNING((byte) 6),
    CHANNEL_TEXT((byte) 7),
    CHANNEL_END((byte) 31);

    private byte channel;

    NetworkChannel(byte channel){
        this.channel = channel;
    }

    public byte getAsByte(){
        return channel;
    }
}
