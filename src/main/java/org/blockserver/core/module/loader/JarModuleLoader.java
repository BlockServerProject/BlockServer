package org.blockserver.core.module.loader;


import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.jar.JarFile;

/**
 * Module Loader that can load modules from JARs
 */
public class JarModuleLoader implements ModuleLoader {
    @Override
    public Collection<Module> setModules(Collection<Module> currentModules, Server server) {
        File moduleFolder = new File("modules");
        if (!moduleFolder.isDirectory()) {
            moduleFolder.mkdirs();
            return currentModules;
        }
        File[] files = moduleFolder.listFiles();

        if (files == null || files.length <= 0)
            return currentModules;

        for (File file : files) {
            if (file.getName().endsWith(".jar")) {
                try {
                    JarFile jar = new JarFile(file);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return currentModules;
    }
}
