package org.blockserver.server.core.services.module;

import java.util.List;

/**
 * Represents a Loader capable of loading
 * external Modules.
 *
 * @author BlockServer Team
 */
public interface ModuleLoader {
    /**
     * Loads a module(s) using the supplied <code>params</code>.
     * <br>
     * The Loader MUST call <code>Module.init</code>
     * @param service The ModuleService that this loader belongs to.
     * @param params Parameters that may or may not be required by the loader.
     * @return A List of modules that have been loaded.
     */
    List<Module> loadModules(ModuleService service, Object... params);
}
