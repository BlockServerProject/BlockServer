package org.blockserver.core.networking.channel;


import org.blockserver.core.networking.protocol.ProtocolType;

import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"SynchronizeOnNonFinalField", "ConstantConditions"})
public class ChannelBase extends ChannelWrapper {
    private ExecutorService executor;
    private ObjectReceiver receiver;

    public ChannelBase(ByteChannel channel, InetSocketAddress address) {
        super(channel, address);
    }

    public ChannelBase(ProtocolType type, String hostname, int port) {
        this(type, new InetSocketAddress(hostname, port));
    }

    public ChannelBase(ProtocolType type, InetSocketAddress address) {
        super(type, address);
        executor = Executors.newFixedThreadPool(2);
    }

    public void start() {
        executor.execute(() -> {
            while (isOpen())
                onReceive(receive());
        });
    }

    public void onReceive(Object object) {
        receiver.receive(object);
    }

    @Override
    public void send(Object packet) {
        executor.execute(() -> super.send(packet));
    }

    @Override
    public void send(Object packet, InetSocketAddress address) {
        executor.execute(() -> super.send(packet, address));
    }

    public ObjectReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(ObjectReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void close() {
        executor.shutdown();
        super.close();
    }

    public interface ObjectReceiver {
        void receive(Object object);
    }
}