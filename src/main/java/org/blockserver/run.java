package org.blockserver;

import org.blockserver.event.events.modules.ModuleEnableEvent;
import org.blockserver.event.system.EventListener;
import org.blockserver.module.loader.CoreModuleLoader;

/**
 * Main class for the core.
 *
 * @author BlockServer team
 */
public class run {
    public static void main(String[] args) {
        Server server = new Server(new CoreModuleLoader());
        server.onEnable();
        new EventListener<ModuleEnableEvent, ModuleEnableEvent>(){
            @Override
            public void onEvent(ModuleEnableEvent event) {
                System.out.print("EventFIRED");
            }
        }.register(ModuleEnableEvent.class, server.getEventManager());
    }
}