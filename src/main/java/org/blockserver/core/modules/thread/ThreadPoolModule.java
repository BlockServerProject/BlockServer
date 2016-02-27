package org.blockserver.core.modules.thread;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.Server;
import org.blockserver.core.module.ServerModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Exerosis.
 */
public class ThreadPoolModule extends ServerModule {
    @Getter @Setter private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public ThreadPoolModule(Server server) {
        super(server);
    }
}