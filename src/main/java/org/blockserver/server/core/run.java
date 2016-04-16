package org.blockserver.server.core;

import java.net.InetSocketAddress;
import java.util.Arrays;

import org.blockserver.server.core.services.module.ModuleService;
import org.blockserver.server.pocket.PocketModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;

/**
 * Main run class for BlockServer
 */
public class run {
    public static void main(String[] args) {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
        Logger logger = LoggerFactory.getLogger("BlockServer");

        Server server = new Server(new InetSocketAddress("0.0.0.0", 19132), logger);
        server.getServiceManager().getService(ModuleService.class).registerLoader((service, params) -> {
            PocketModule m = new PocketModule();
            m.init(server);
            return Arrays.asList(m);
        });
        server.setRunning(true);
        server.run();
    }
}
