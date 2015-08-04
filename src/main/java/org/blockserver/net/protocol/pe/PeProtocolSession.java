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
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Represents a session of a player.
 */
public class PeProtocolSession implements ProtocolSession, PeProtocolConst{
    private ProtocolManager mgr;
    private RakNetBridge bridge;
    private InetSocketAddress addr;
    private PeProtocol pocketProtocol;

    public PeProtocolSession(ProtocolManager mgr, RakNetBridge bridge, InetSocketAddress addr, PeProtocol pocket){
        this.mgr = mgr;
        this.bridge = bridge;
        this.addr = addr;
        pocketProtocol = pocket;
    }

    @Override
    public void handlePacket(WrappedPacket pk) {
        switch (pk.bb().get()){
            case RakNetBridge.PACKET_DATA_PACKET:
                handleDataPacket(pk);
                break;

            case RakNetBridge.PACKET_CLOSE_SESSION:
                closeSession("unknown"); //TODO
                break;
        }
    }

    /**
     * Handles a Minecraft Data Packet
     * @param pk
     */
    private void handleDataPacket(WrappedPacket pk) {
        byte pid = pk.bb().get();
        switch(pid){
            case LOGIN_PACKET:
                getServer().getLogger().info("Login packet!");
                break;
        }
    }

    @Override
    public SocketAddress getAddress() {
        return addr;
    }

    @Override
    public void sendPacket(byte[] buffer) {

    }

    @Override
    public void sendResponse(InternalResponse response) {

    }

    @Override
    public void closeSession(String reason) {

    }

    @Override
    public Server getServer() {
        return mgr.getServer();
    }
}
