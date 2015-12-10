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
package org.blockserver.core.module.loaders;


import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.module.ModuleLoader;

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
