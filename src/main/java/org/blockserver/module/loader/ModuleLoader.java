package org.blockserver.module.loader;

import org.blockserver.Server;
import org.blockserver.module.Module;

import java.util.Collection;

public interface ModuleLoader {
    Collection<Module> setModules(Collection<Module> currentModules, Server server);
}