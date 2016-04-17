package org.blockserver.server.core.module;

import org.blockserver.server.core.Server;

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
     * @param server The Server this loader belongs to.
     * @param params Parameters that may or may not be required by the loader.
     * @return A List of modules that have been loaded.
     */
    List<Module> loadModules(Server server, Object... params);
}
