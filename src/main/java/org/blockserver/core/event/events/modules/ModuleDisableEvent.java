package org.blockserver.core.event.events.modules;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

public class ModuleDisableEvent extends ModuleEvent {
    public ModuleDisableEvent(Server server, Module module) {
        super(server, module);
    }
}