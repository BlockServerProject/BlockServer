package org.blockserver.core.networking.channel;


import org.blockserver.core.networking.protocol.ProtocolType;
import org.blockserver.core.utilities.StreamUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

@SuppressWarnings({"SynchronizeOnNonFinalField", "ConstantConditions"})
public class ChannelWrapper {
    private final ProtocolType type;
    private InetSocketAddress address;
    private ByteChannel channel;

    public ChannelWrapper(ByteChannel channel, InetSocketAddress address) {
        this(channel instanceof DatagramChannel ? ProtocolType.UDP : ProtocolType.TCP, address);
    }

    public ChannelWrapper(ProtocolType type, String hostName, int port) {
        this(type, new InetSocketAddress(hostName, port));
    }

    public ChannelWrapper(ProtocolType type, InetSocketAddress socketAddress) {
        this.type = type;
        address = socketAddress;

        try {
            if (type.equals(ProtocolType.UDP)) {
                channel = DatagramChannel.open();
                ((DatagramChannel) channel).socket().bind(socketAddress);
            }
            else {
                channel = SocketChannel.open();
                ((SocketChannel) channel).connect(socketAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receive() {
        synchronized (channel) {
            InputStream stream = null;
            try {
                ByteBuffer buffer = ByteBuffer.allocate(48);
                buffer.clear();

                if (type.equals(ProtocolType.UDP))
                    ((DatagramChannel) channel).receive(buffer);
                else
                    channel.read(buffer);

                stream = new ByteArrayInputStream(buffer.array());
                stream = new BufferedInputStream(stream);
                stream = new ObjectInputStream(stream);
                return ((ObjectInputStream) stream).readObject();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StreamUtil.closeQuietly(stream);
            }
        }
        return null;
    }

    public void send(Object packet) {
        send(packet, null);
    }

    public void send(Object packet, InetSocketAddress address) {
        synchronized (channel) {
            OutputStream stream = null;
            try {
                stream = new ByteArrayOutputStream();
                stream = new BufferedOutputStream(stream);
                stream = new ObjectOutputStream(stream);
                ((ObjectOutputStream) stream).writeObject(packet);
                ByteBuffer buffer = ByteBuffer.wrap(((ByteArrayOutputStream) stream).toByteArray());
                if (type.equals(ProtocolType.UDP) && address != null)
                    ((DatagramChannel) channel).send(buffer, address);
                else
                    channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.closeQuietly(stream);
            }
        }
    }

    public ProtocolType getType() {
        return type;
    }

    public boolean isOpen() {
        return channel.isOpen();
    }

    public void close() {
        StreamUtil.closeQuietly(channel);
    }

    public Channel getChannel() {
        return channel;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}