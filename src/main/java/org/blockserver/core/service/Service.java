package org.blockserver.core.service;

import lombok.Getter;
import org.blockserver.core.Server;

/**
 * Represents a service which can be started and stopped.
 */
public abstract class Service {
    @Getter private final String name;
    @Getter private final String version;
    @Getter private final ServiceManager serviceManager;
    @Getter private boolean running = false;

    public Service(ServiceManager manager, String name, String version) {
        this.serviceManager = manager;

        this.name = name;
        this.version = version;
    }

    public final void start() {
        if(isRunning()) return;
        serviceManager.getServer().getLogger().info("Starting service "+name+" "+version);
        this.running = true;
        _start();
        serviceManager.getServer().getLogger().info("Service "+name+" started");
    }

    public final void stop() {
        if(!isRunning()) return;
        serviceManager.getServer().getLogger().info("Stopping service "+name+" "+version);
        this.running = false;
        _stop();
        serviceManager.getServer().getLogger().info("Service "+name+" stopped");
    }

    protected void _start() {

    }

    protected void _stop() {

    }
}
