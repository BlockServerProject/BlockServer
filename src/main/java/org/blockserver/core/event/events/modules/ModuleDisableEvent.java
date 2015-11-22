package org.blockserver.core.event.events.modules;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.event.Cancellable;
import org.blockserver.core.event.events.ServerEvent;
import org.blockserver.core.module.Module;

/**
 * Event fired when a module is disabled.
 *
 * @author BlockServer Team
 */
public class ModuleDisableEvent extends ServerEvent implements Cancellable {
    @Getter private final Module module;

    public ModuleDisableEvent(Server server, Module module) {
        super(server);
        this.module = module;
    }
}
