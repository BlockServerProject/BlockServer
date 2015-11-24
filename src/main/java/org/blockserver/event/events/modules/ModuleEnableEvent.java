package org.blockserver.event.events.modules;

import org.blockserver.Server;
import org.blockserver.module.Module;

public class ModuleEnableEvent extends ModuleEvent {
    public ModuleEnableEvent(Server server, Module module) {
        super(server, module);
    }
}