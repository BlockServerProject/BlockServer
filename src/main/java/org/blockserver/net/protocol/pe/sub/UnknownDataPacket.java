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
package org.blockserver.net.protocol.pe.sub;

/**
 * Created by jython234 on 8/3/2015.
 */
public class UnknownDataPacket extends PeDataPacket{
    private byte[] buffer;

    public UnknownDataPacket(byte[] buffer){
        this.buffer = buffer;
    }

    @Override
    protected int getLength() {
        return buffer.length;
    }

    @Override
    public byte getPID() {
        return buffer[0];
    }
}
