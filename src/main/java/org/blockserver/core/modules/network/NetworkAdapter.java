package org.blockserver.core.modules.network;

import lombok.Getter;
import org.blockserver.core.modules.network.message.Message;

public abstract class NetworkAdapter {
    @Getter private NetworkProvider provider; //Set in constructor

    protected abstract Message packetToMessage(RawPacket packet);

    protected abstract RawPacket messageToPacket(Message message);
}