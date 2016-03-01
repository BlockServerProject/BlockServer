package org.blockserver.core.module.loader;

import org.blockserver.core.module.Module;

import java.util.List;

/**
 * Represents a loader that can load modules.
 */
public interface ModuleLoader {

    List<Module> loadModules();
}
