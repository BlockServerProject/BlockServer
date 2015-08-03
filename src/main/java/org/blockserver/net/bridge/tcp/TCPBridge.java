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
package org.blockserver.net.bridge.tcp;

import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.protocol.WrappedPacket;

import java.net.SocketAddress;

/**
 * A Network Bridge for the TCP/IP protocol.
 */
public class TCPBridge extends NetworkBridge{
    private Server server;
    private NonBlockTCPSocket socket;

    public TCPBridge(Server server, SocketAddress bindAddress, Class<? extends TCPConnectionHandler> handlerImpl){
        this.server = server;
        socket = new NonBlockTCPSocket(this, bindAddress,handlerImpl);
        socket.start();
    }

    @Override
    public boolean send(byte[] buffer, SocketAddress addr) {
        WrappedPacket wp = new WrappedPacket(buffer, addr, this);
        socket.send(wp);
        return true;
    }

    @Override
    public WrappedPacket receive() {
        return socket.receive_();
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public String getDescription() {
        return "A network bridge for the TCP/IP protocol.";
    }
}
