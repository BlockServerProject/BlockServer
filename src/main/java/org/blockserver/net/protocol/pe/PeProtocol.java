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
package org.blockserver.net.protocol.pe;

import org.blockserver.Server;
import org.blockserver.net.bridge.raknet.RakNetBridge;
import org.blockserver.net.protocol.Protocol;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;

/**
 * Created by jython234 on 8/3/2015.
 */
public class PeProtocol extends Protocol{
    private Server server;

    public PeProtocol(Server server){
        this.server = server;
    }

    @Override
    public ProtocolSession openSession(WrappedPacket pk) {
        byte pid = pk.bb().get();
        switch (pid){
            case RakNetBridge.PACKET_OPEN_SESSION:

                return null;
                //break;

            default:
                return null;
        }
    }

    @Override
    public String getDescription() {
        return "Supports MCPE and MCW10 clients.";
    }
}
