package org.blockserver.server.core.services.module;

import org.blockserver.server.core.Server;
import org.blockserver.server.core.service.Service;
import org.blockserver.server.core.service.ServiceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service that handles module loading, enabling, and disabling.
 *
 * @author BlockServer Team
 */
public class ModuleService extends Service {

    private List<ModuleLoader> loaders = new ArrayList<>();
    private List<Module> modules = new CopyOnWriteArrayList<>();

    public ModuleService(ServiceManager manager) {
        super(manager, "ModuleService", "internal-"+ Server.SOFTWARE_VERSION);
    }

    public synchronized void registerLoader(ModuleLoader loader) {
        if(!this.loaders.contains(loader)) {
            this.loaders.add(loader);
            return;
        }
        throw new IllegalArgumentException("Loader already registered!");
    }

    @Override
    protected void _start() {
        if(!loaders.isEmpty()) {
            loaders.stream().forEach(loader -> {
                getLogger().debug("ModuleLoader: "+loader.getClass().getCanonicalName());
                List<Module> modules = loader.loadModules(this);
                if(!modules.isEmpty()) getLogger().debug("Loaded "+modules.size()+" modules.");

                modules.stream().forEach(module -> {
                    getLogger().info("Enabling Module: "+module.getClass().getSimpleName());
                    module.onEnable();
                });
                this.modules.addAll(modules);
            });
        }
    }

    @Override
    protected void _stop() {

    }
}
