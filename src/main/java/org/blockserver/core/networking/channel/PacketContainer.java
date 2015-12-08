package org.blockserver.core.networking.channel;

import java.net.InetSocketAddress;

public class PacketContainer {
    private final Object packet;
    private final InetSocketAddress address;
    private final long sendTime;
    private long receiveTime;

    protected PacketContainer(Object packet, ChannelBase channel) {
        this.packet = packet;
        address = channel.getAddress();
        sendTime = System.currentTimeMillis();
    }

    protected void receive(ChannelBase server) {
        receiveTime = System.currentTimeMillis();
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public long getSendTime() {
        return sendTime;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public Object getPacket() {
        return packet;
    }
}