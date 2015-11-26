package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.network.message.InboundMessage;
import org.blockserver.core.modules.network.message.OutboundMessage;

public abstract class NetworkAdapter extends Module {
    public NetworkAdapter(Server server) {
        super(server);
    }

    public abstract void sendPacket(OutboundMessage packet);

    public InboundMessage receivePacket(InboundMessage packet) {
        return getServer().getEventManager().fire(packet, event -> {
            if (event.isCancelled())
                event = null;
        });
    }
}