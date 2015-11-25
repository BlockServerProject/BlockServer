package org.blockserver.event.events.modules;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.Server;
import org.blockserver.event.events.ServerEvent;
import org.blockserver.event.system.Cancellable;
import org.blockserver.module.Module;

public class ModuleEvent extends ServerEvent implements Cancellable {
    @Getter @Setter private Module module;

    public ModuleEvent(Server server, Module module) {
        super(server);
        this.module = module;
    }
}