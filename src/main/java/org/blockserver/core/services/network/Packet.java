package org.blockserver.core.services.network;

import java.net.SocketAddress;

/**
 * A piece of information sent over the network.
 *
 * @author BlockServer Team
 */
public class Packet {
    public byte[] buffer;
    public SocketAddress address;
}
