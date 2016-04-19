package org.blocksever.test.core.test.networking;

import org.blocksever.test.core.networking.NetworkDispatcher;
import org.blocksever.test.core.networking.NetworkManager;
import org.blocksever.test.core.networking.RawPacket;

/**
 * Created by Exerosis.
 */
public class PENetworkHandler implements NetworkDispatcher {
    private NetworkManager manager;

    public PENetworkHandler(NetworkManager manager) {
        this.manager = manager;
    }

    public void testDisptach(RawPacket packet){
        System.out.println("Recived " + packet.toString() + " from PE client!");
        manager.dispatch(packet);
    }

    @Override
    public void dispatch(RawPacket packet) {
        System.out.println("Dispatching packet, to PE client! " + packet.toString());
    }
}