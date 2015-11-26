package org.blockserver.core.event.events.modules;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.Server;
import org.blockserver.core.event.events.ServerEvent;
import org.blockserver.core.event.system.Cancellable;
import org.blockserver.core.module.Module;

public class ModuleEvent extends ServerEvent implements Cancellable {
    @Getter @Setter private Module module;

    public ModuleEvent(Server server, Module module) {
        super(server);
        this.module = module;
    }
}