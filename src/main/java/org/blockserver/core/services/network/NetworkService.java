package org.blockserver.core.services.network;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.blockserver.core.Server;
import org.blockserver.core.service.Service;
import org.blockserver.core.service.ServiceManager;

/**
 * A service implementation which handles the server's
 * networking
 *
 * @author BlockServer team
 */
public class NetworkService extends Service {
    private final List<NetworkProvider> providers = new ArrayList<>();

    public NetworkService(ServiceManager manager, InetSocketAddress bindAddress) {
        super(manager, "NetworkServer", "internal-"+ Server.SOFTWARE_VERSION);
    }

    public void registerProvider(NetworkProvider provider) {
        synchronized (this.providers) {
            providers.add(provider);
        }
    }

    @Override
    protected void _start() {

    }
}
