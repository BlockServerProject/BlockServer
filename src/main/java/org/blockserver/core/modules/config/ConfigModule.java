package org.blockserver.core.modules.config;

import org.blockserver.core.Server;
import org.blockserver.core.module.ServerModule;
import org.blockserver.core.modules.file.FileModule;

/**
 * Created by Exerosis.
 */
public class ConfigModule extends ServerModule {
    private final FileModule fileModule;

    public ConfigModule(Server server, FileModule fileModule) {
        super(server);
        this.fileModule = fileModule;
    }
}