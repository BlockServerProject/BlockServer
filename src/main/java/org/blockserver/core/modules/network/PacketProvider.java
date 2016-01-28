package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PacketProvider extends Module implements Provider {
    private final BlockingQueue<RawPacket> queue = new LinkedBlockingQueue<>();

    public PacketProvider(Server server) {
        super(server);
    }

    @Override
    public RawPacket poll() {
        return queue.poll();
    }

    @Override
    public RawPacket peek() {
        return queue.peek();
    }

    public void provide(RawPacket packet) {
        queue.add(packet);
    }
}
