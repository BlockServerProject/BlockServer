package org.blockserver.core.module.loader;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.util.Collection;

public interface ModuleLoader {
    Collection<Module> setModules(Collection<Module> currentModules, Server server);
}