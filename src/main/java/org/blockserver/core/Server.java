package org.blockserver.core;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.events.modules.ModuleDisableEvent;
import org.blockserver.core.event.events.modules.ModuleEnableEvent;
import org.blockserver.core.event.system.EventManager;
import org.blockserver.core.module.Enableable;
import org.blockserver.core.module.Module;
import org.blockserver.core.module.loader.ModuleLoader;
import org.blockserver.core.ticker.ServerTicker;

import java.util.*;

/**
 * Represents the core server implementation.
 *
 * @author BlockServer Team
 */
public class Server implements Enableable {
    @Getter @Setter private EventManager eventManager = new EventManager();
    @Getter private ServerTicker ticker;
    private boolean enabled;

    //Modules
    private Map<Class<? extends Module>, Module> modules = new HashMap<>();

    public Server(ModuleLoader... moduleLoaders) {
        Collection<Module> modules = new ArrayList<>(this.modules.values());
        this.modules.clear();

        for (ModuleLoader moduleLoader : moduleLoaders) {
            modules = moduleLoader.setModules(modules, this);
        }

        for (Module module : modules) {
            this.modules.put(module.getClass(), module);
        }

        ticker = new ServerTicker(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> moduleClass) {
        return (T) modules.get(moduleClass);
    }

    @Override
    public void onEnable() {
        enabled = true;
        modules.values().forEach((module) -> {
            if (module.isEnabled())
                return;
            eventManager.fire(new ModuleEnableEvent(this, module), event -> {
                if (!event.isCancelled())
                    module.onEnable();
            });
        });
        ticker.setRunning(true);
        ticker.run();
    }

    @Override
    public void onDisable() {
        enabled = false;
        modules.values().forEach((module) -> {
            if (!module.isEnabled())
                return;
            eventManager.fire(new ModuleDisableEvent(this, module), event -> {
                if (!event.isCancelled())
                    module.onDisable();
            });
        });
        ticker.setRunning(false);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}