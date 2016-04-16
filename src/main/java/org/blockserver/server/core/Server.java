package org.blockserver.server.core;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.server.core.service.ServiceManager;
import org.blockserver.server.core.services.module.ModuleService;
import org.blockserver.server.core.services.network.NetworkService;
import org.blockserver.server.core.util.Task;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * The main server implementation.
 *
 * @author BlockServer Team
 */
public class Server implements Runnable {
    public static final String SOFTWARE = "BlockServer";
    public static final String SOFTWARE_VERSION = "1.0-SNAPSHOT";

    @Getter private InetSocketAddress bindAddress;

    @Getter private ServiceManager serviceManager;
    @Getter private final Logger logger;

    @Setter
    @Getter private boolean running = false;
    
    private long currentTick = 0;
    private List<Task> tasks = new ArrayList<>();

    public Server(InetSocketAddress bindAddress, Logger logger) {
        this.logger = logger;
        this.serviceManager = new ServiceManager(this);

        this.bindAddress = bindAddress;

        registerServices();
    }

    private void registerServices() {
        serviceManager.registerService(new ModuleService(serviceManager));
        serviceManager.registerService(new NetworkService(serviceManager, bindAddress));
    }

    @Override
    public void run() {
        if(!running) return;
        logger.info("This server is running "+Server.SOFTWARE+" "+Server.SOFTWARE_VERSION+" on "+System.getProperty("os.name")+" "+System.getProperty("os.version"));

        serviceManager.startAllServices();

        boolean exception = false;
        while(isRunning()) { // Begin ticking
            long start = System.currentTimeMillis();
            try {
                tick();
            } catch (Exception e) {
                logger.error("FATAL! "+e.getClass().getName()+" while ticking! "+e.getMessage());
                e.printStackTrace();
                exception = true;
            }
            long elapsed = System.currentTimeMillis() - start;
            if(elapsed > 50) { // 20 TPS
                logger.warn("Can't keep up! ("+elapsed+">50) Did the system time change or is the server overloaded?");
            } else {
                try {
                    Thread.sleep(50 - elapsed);
                } catch (InterruptedException e) {
                    logger.error("FATAL! failed to sleep while ticking! java.lang.InterruptedException: "+e.getMessage());
                    e.printStackTrace();
                    exception = true;
                }
            }
        }
        
        logger.info("Stopping server...");
        this.serviceManager.stopAllServices();
        logger.info("Halting...");
        System.exit(exception ? 1 : 0);
    }

    private void tick() throws Exception {
        if(tasks.isEmpty()) return;
        List<Task> toRemove = new ArrayList<>();
        tasks.stream().forEach(task -> {
           if(currentTick == task.runAt) {
               task.r.run();
               toRemove.add(task);
           }
        });
        tasks.removeAll(toRemove);
    }
}
