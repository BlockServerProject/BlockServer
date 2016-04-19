package org.blocksever.test.core.test.networking;

import org.blocksever.test.core.networking.NetworkDispatcher;
import org.blocksever.test.core.networking.RawPacket;

/**
 * Created by Exerosis.
 */
public class EventDispatcher implements NetworkDispatcher {

    public EventDispatcher() {
        System.out.println("Created Event Dispatcher");
    }

    @Override
    public void dispatch(RawPacket packet) {
        System.out.println("Firing RawPacketEvent for packet: " + packet.toString());
    }
}