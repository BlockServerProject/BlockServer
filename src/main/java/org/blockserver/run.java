package org.blockserver;

import org.blockserver.core.Server;
import org.blockserver.core.module.loader.CoreModuleLoader;

/**
 * Main class for the core.
 *
 * @author BlockServer team
 */
public class run {
    public static void main(String[] args) {
        Server server = new Server(new CoreModuleLoader());
        server.onEnable();
    }
}