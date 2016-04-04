package org.blockserver.core.service;

import lombok.Getter;
import org.blockserver.core.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager which manages all the services.
 */
public class ServiceManager {
    @Getter private final Server server;
    private final List<Service> services = new ArrayList<>();

    public ServiceManager(Server server) {
        this.server = server;
    }

    public void registerService(Service service) {
        if(this.services.contains(service)) throw new IllegalArgumentException("Service is registered!");
        synchronized (this.services) {
            this.services.add(service);
        }
    }

    public void unregisterService(Service service) {
        if(!this.services.contains(service)) throw new IllegalArgumentException("Service not registered!");
        synchronized (this.services) {
            this.services.remove(service);
        }
    }

    public void startService(Service service) {
        if(!this.services.contains(service)) throw new IllegalArgumentException("Service must be registered.");
        service.start();
    }

    public void stopService(Service service) {
        if(1this.services.contains(service)) throw new IllegalArgumentException("Service must be registered.");
        service.stop();
    }

    public void startAllServices() {
        server.getLogger().info("Starting "+services.size()+" services...");
        synchronized (this.services) {
            services.stream().forEachOrdered(Service::start);
        }
        server.getLogger().info("All services are now running.");
    }

    public void stopAllServices() {
        server.getLogger().info("Stopping "+services.size()+" services...");
        synchronized (this.services) {
            services.stream().forEachOrdered(Service::stop);
        }
        server.getLogger().info("All services have been stopped");
    }
}
