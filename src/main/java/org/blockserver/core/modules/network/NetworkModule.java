package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.logging.LoggingModule;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manager for the network.
 *
 * @author BlockServer Team
 */
public class NetworkModule extends Module {
    private List<NetworkAdapter> adapters = new CopyOnWriteArrayList<>();
    private LoggingModule logger;

    public NetworkModule(Server server) {
        super(server);
    }

    @Override
    public void onEnable() {
        logger = getServer().getModule(LoggingModule.class);
        logger.info("Network Module enabled.");
    }

    @Override
    public void onDisable() {
        //TODO: close adapters and providers
        logger.info("Network Module disabled.");
    }
}
