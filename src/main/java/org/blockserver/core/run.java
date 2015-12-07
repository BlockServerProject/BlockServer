package org.blockserver.core;

import org.blockserver.core.event.events.modules.ModuleEnableEvent;
import org.blockserver.core.event.system.EventListener;
import org.blockserver.core.module.loader.CoreModuleLoader;

/**
 * Main class for the core.
 *
 * @author BlockServer team
 */
public class run {

    public static void main(String[] args) {
        Server server = new Server(new CoreModuleLoader());

        new EventListener<ModuleEnableEvent, ModuleEnableEvent>(){
            @Override
            public void onEvent(ModuleEnableEvent event) {
                System.out.print(event.getModule());
            }
        }.register(ModuleEnableEvent.class, server.getEventManager());

        Runtime.getRuntime().addShutdownHook(new Thread(server::onDisable));
        server.onEnable();
    }
}