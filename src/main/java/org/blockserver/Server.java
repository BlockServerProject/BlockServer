package org.blockserver;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.event.EventManager;
import org.blockserver.logging.LoggingModule;
import org.blockserver.module.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the core server implementation.
 *
 * @author BlockServer Team
 */
public class Server implements Runnable {
    @Getter @Setter private EventManager eventManager = new EventManager();
    @Getter @Setter private boolean running = false;

    //Modules
    private Map<Class<? extends Module>, Module> modules = new HashMap<>();
    @Getter private LoggingModule logger;

    public Server() {
        loadModules();
    }

    private void loadModules() {
        logger = new LoggingModule(this);
        logger.onEnable();
    }

    @Override
    public void run() {
        logger.info("Testing module system.");
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> moduleClass) {
        return (T) modules.get(moduleClass);
    }
}