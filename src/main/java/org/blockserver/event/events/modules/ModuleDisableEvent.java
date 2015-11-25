package org.blockserver.event.events.modules;

import org.blockserver.Server;
import org.blockserver.module.Module;

public class ModuleDisableEvent extends ModuleEvent {
    public ModuleDisableEvent(Server server, Module module) {
        super(server, module);
    }
}