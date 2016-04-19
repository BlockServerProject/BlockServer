package org.blocksever.test.core.test;

import org.blocksever.test.core.Module;
import org.blocksever.test.core.networking.NetworkManager;
import org.blocksever.test.core.networking.RawPacket;
import org.blocksever.test.core.test.networking.EventDispatcher;
import org.blocksever.test.core.test.networking.PENetworkHandler;

/**
 * Created by Exerosis.
 */
public class TestNetworkModule extends Module {
    private final NetworkManager inboundManager = new NetworkManager();
    private final NetworkManager outboundManager = new NetworkManager();

    public TestNetworkModule() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        PENetworkHandler peNetworkHandler = new PENetworkHandler(inboundManager);
        inboundManager.registerDispatcher(eventDispatcher);
        outboundManager.registerDispatcher(eventDispatcher);

        outboundManager.registerDispatcher(peNetworkHandler);

        peNetworkHandler.testDisptach(new RawPacket("MovePacket"));
        peNetworkHandler.testDisptach(new RawPacket("InteractPacket"));
        peNetworkHandler.testDisptach(new RawPacket("ChatPacket"));

        outboundManager.dispatch(new RawPacket("WeatherPacket"));
        outboundManager.dispatch(new RawPacket("ChunkPacket"));
        outboundManager.dispatch(new RawPacket("TimePacket"));
    }

    @Override
    protected void onEnable() {
        System.out.println("Enabled network test module");
    }

    @Override
    protected void onDisable() {
        System.out.println("Disabled network test module");
    }
}
