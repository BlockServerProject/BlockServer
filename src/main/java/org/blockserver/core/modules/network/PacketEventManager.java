package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.event.Priority;
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.events.packets.PacketReceiveEvent;
import org.blockserver.core.events.packets.PacketSendEvent;

public class PacketEventManager extends NetworkProvider implements Dispatcher {
    private final ServerEventListener<PacketSendEvent> listener;

    public PacketEventManager(NetworkHandler handler, Server server) {
        super(handler, server);
        listener = new ServerEventListener<PacketSendEvent>() {
            @Override
            public void onEvent(PacketSendEvent event) {
                if (!event.isCancelled())
                    provide(event.getPacket());
            }
        }.priority(Priority.INTERNAL).post();
    }

    @Override
    public void onEnable() {
        listener.register(PacketSendEvent.class, getServer());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        listener.unregister(getServer());
        super.onDisable();
    }

    @Override
    public void dispatch(RawPacket packet) {
        getServer().getEventManager().fire(new PacketReceiveEvent(packet));
    }
}