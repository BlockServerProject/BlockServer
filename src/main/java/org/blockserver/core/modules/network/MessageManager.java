package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.event.Priority;
import org.blockserver.core.event.ServerEventListener;
import org.blockserver.core.events.messages.MessageReceiveEvent;
import org.blockserver.core.events.messages.MessageSendEvent;

public class MessageManager extends PacketProvider implements Dispatcher {
    private final ServerEventListener<MessageSendEvent> listener;
    private final NetworkConverter converter;

    public MessageManager(Server server, NetworkConverter converter) {
        super(server);
        this.converter = converter;
        listener = new ServerEventListener<MessageSendEvent>() {
            @Override
            public void onEvent(MessageSendEvent event) {
                if (!event.isCancelled())
                    provide(converter.toPacket(event.getMessage()));
            }
        }.priority(Priority.INTERNAL).post();
    }

    @Override
    public void onEnable() {
        listener.register(MessageSendEvent.class, getServer());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        listener.unregister(getServer());
        super.onDisable();
    }

    @Override
    public void dispatch(RawPacket packet) {
        getServer().getExecutorService().execute(() -> getServer().getEventManager().fire(new MessageReceiveEvent<>(converter.toMessage(packet))));
    }
}