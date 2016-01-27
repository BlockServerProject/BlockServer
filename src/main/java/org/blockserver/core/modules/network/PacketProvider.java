package org.blockserver.core.modules.network;


import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class PacketProvider extends Module implements Provider {
    private final BlockingQueue<RawPacket> input = new LinkedBlockingQueue<>();

    public PacketProvider(Server server) {
        super(server);
    }

    @Override
    public void onDisable() {
        input.clear();
        super.onDisable();
    }

    @Override
    public RawPacket poll() {
        return input.poll();
    }

    @Override
    public RawPacket peek() {
        return input.peek();
    }

    public void queuePacket(RawPacket packet) {
        input.add(packet);
    }
}