package org.blockserver.server.pocket;

import org.blockserver.server.core.services.module.Module;
import org.blockserver.server.core.services.network.NetworkService;

import java.net.InetSocketAddress;

/**
 * Created by atzei on 4/16/2016.
 */
public class PocketModule extends Module {
    @Override
    protected void onEnable() {
        NetworkService net = getServer().getServiceManager().getService(NetworkService.class);
        net.registerProvider(new RakNetProvider(net, new InetSocketAddress("0.0.0.0", 19132)));
    }
}
