package org.blockserver.core.module.loader;

import org.blockserver.core.Server;
import org.blockserver.core.modules.logging.LoggingModule;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.network.NetworkModule;

import java.util.Collection;

public class CoreModuleLoader implements ModuleLoader {
    @Override
    public Collection<Module> setModules(Collection<Module> currentModules, Server server) {
        LoggingModule logger = new LoggingModule(server); //Logging is always the first module enabled
        currentModules.add(logger);
        NetworkModule network = new NetworkModule(server);
        currentModules.add(network);
        return currentModules;
    }
}
