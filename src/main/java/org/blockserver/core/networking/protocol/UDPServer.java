package org.blockserver.core.networking.protocol;

import org.blockserver.core.networking.ServerBase;
import org.blockserver.core.networking.channel.ChannelBase;
import org.blockserver.core.utilities.NetworkUtil;

import java.net.InetSocketAddress;

public class UDPServer extends ServerBase {
    private final ChannelBase channel;

    public UDPServer(int port) {
        channel = new ChannelBase(ProtocolType.UDP, new InetSocketAddress(NetworkUtil.getLocalHost(), port));
        channel.setReceiver(object -> receive(object, channel.getAddress()));
    }

    protected void send(Object packet, InetSocketAddress address) {
        channel.send(packet, address);
    }
}