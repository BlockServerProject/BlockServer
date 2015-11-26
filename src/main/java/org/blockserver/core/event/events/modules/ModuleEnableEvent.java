package org.blockserver.core.event.events.modules;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

public class ModuleEnableEvent extends ModuleEvent {
    public ModuleEnableEvent(Server server, Module module) {
        super(server, module);
    }
}