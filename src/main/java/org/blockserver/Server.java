package org.blockserver;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.event.EventManager;
import org.blockserver.logging.LoggingModule;
import org.blockserver.module.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the core server implementation.
 *
 * @author BlockServer Team
 */
public class Server implements Runnable {
    @Getter @Setter private EventManager eventManager = new EventManager();
    @Getter @Setter private boolean running = false;

    //Modules
    private List<Module> modulesLoaded = new ArrayList<>();
    @Getter private LoggingModule logger;

    public Server() {
        loadModules();
    }

    private void loadModules() {
        logger = new LoggingModule(this);
        logger.onEnable();
        modulesLoaded.add(logger);
    }

    @Override
    public void run() {
        logger.info("Testing module system.");
    }

    public Module getModule(String name) {
        for(Module module : modulesLoaded) {
            if(module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }
}