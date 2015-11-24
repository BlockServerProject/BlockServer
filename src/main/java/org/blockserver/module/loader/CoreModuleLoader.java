package org.blockserver.module.loader;

import org.blockserver.Server;
import org.blockserver.logging.LoggingModule;
import org.blockserver.module.Module;

import java.util.Collection;

public class CoreModuleLoader implements ModuleLoader {
    @Override
    public Collection<Module> setModules(Collection<Module> currentModules, Server server) {
        LoggingModule logger = new LoggingModule(server);
        currentModules.add(logger);
        return currentModules;
    }
}
