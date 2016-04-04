package org.blockserver.core.service;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Represents a service which can be started and stopped.
 */
public abstract class Service {
    @Getter private final String name;
    @Getter private final String version;
    @Getter private final ServiceManager serviceManager;
    @Getter(AccessLevel.PROTECTED) private final ServiceLogger logger;

    @Getter private boolean running = false;

    public Service(ServiceManager manager, String name, String version) {
        this.serviceManager = manager;

        this.logger = new ServiceLogger(this, manager.getServer().getLogger());
        this.name = name;
        this.version = version;
    }

    protected final void start() {
        if(isRunning()) return;
        serviceManager.getServer().getLogger().info("Starting service "+name+" "+version);
        this.running = true;
        _start();
        serviceManager.getServer().getLogger().debug("Service "+name+" started");
    }

    protected final void stop() {
        if(!isRunning()) return;
        serviceManager.getServer().getLogger().info("Stopping service "+name+" "+version);
        this.running = false;
        _stop();
        serviceManager.getServer().getLogger().debug("Service "+name+" stopped");
    }

    protected void _start() {

    }

    protected void _stop() {

    }
}
