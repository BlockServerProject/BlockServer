package org.blockserver.server.core.services.network;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.blockserver.server.core.Server;
import org.blockserver.server.core.service.Service;
import org.blockserver.server.core.service.ServiceManager;
import org.blockserver.server.core.util.MiscUtils;

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
        getServiceManager().getServer().addRepeatingTask(1, this::tick);
    }

    public void registerProvider(NetworkProvider provider) {
        synchronized (this.providers) {
            providers.add(provider);
        }
    }

    private void tick() {
        providers.stream().forEach(provider -> {
            int max = 500;
            Packet pk;
            while((pk = provider.getNextPacket()) != null && max > 0) {
                getLogger().debug("IN: "+ MiscUtils.bytesToHexString(pk.buffer));
                max--;
            }
        });
    }

    @Override
    protected void _start() {
        providers.stream().forEach(NetworkProvider::bind);
    }

    @Override
    protected void _stop() {
        providers.stream().forEach(NetworkProvider::close);
    }
}
