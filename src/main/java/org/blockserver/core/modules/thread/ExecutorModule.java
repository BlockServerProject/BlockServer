package org.blockserver.core.modules.thread;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.Server;
import org.blockserver.core.module.ServerModule;
import org.blockserver.core.modules.config.ConfigModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Exerosis.
 */
public class ExecutorModule extends ServerModule {
    private final ConfigModule configModule;
    @Getter @Setter private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public ExecutorModule(Server server, ConfigModule configModule) {
        super(server);
        this.configModule = configModule;
    }
}