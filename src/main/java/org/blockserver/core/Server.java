/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.EventManager;
import org.blockserver.core.event.events.ModuleDisableEvent;
import org.blockserver.core.event.events.ModuleEnableEvent;
import org.blockserver.core.module.EnableableImplementation;
import org.blockserver.core.module.Module;
import org.blockserver.core.module.ModuleLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents the core server implementation.
 *
 * @author BlockServer Team
 */
public class Server implements EnableableImplementation {
    @Getter @Setter private ExecutorService executorService = Executors.newFixedThreadPool(4);
    @Getter @Setter private EventManager eventManager = new EventManager();
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
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}