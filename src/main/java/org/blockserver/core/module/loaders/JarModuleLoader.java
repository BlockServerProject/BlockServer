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
import org.blockserver.core.module.ModuleLoader;
import org.blockserver.core.module.ServerModule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarFile;

/**
 * ServerModule Loader that can load modules from JARs
 *
 * @author BlockServer Team
 * @see org.blockserver.core.module.ModuleLoader
 */
public class JarModuleLoader implements ModuleLoader {
    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public void setModules(Map<Class<? extends ServerModule>, ServerModule> modules, Server server) {
        File moduleFolder = new File("Modules");
        if (moduleFolder.mkdirs())
            System.err.println("Could not find modules folder, created modules folder!");
        File[] files = moduleFolder.listFiles();
        System.err.println(files.length);
        if (files == null || files.length <= 0)
            return;

        for (File file : files) {
            System.err.println(file.getName());
            if (file.getName().endsWith(".jar")) {
                try {
                    JarFile jar = new JarFile(file);
                    Properties jarProp = getJarProperties(jar);
                    URLClassLoader loader = new URLClassLoader(new URL[]{file.toURL()});
                    String className = jarProp.getProperty("mainClass", "default");
                    try {
                        Class clazz = loader.loadClass(className);
                        try {
                            ServerModule module = (ServerModule) clazz.getConstructor(Server.class).newInstance(server);
                            modules.put(module.getClass(), module);
                            System.out.println("[ServerModule Loader]: Loaded " + file.getName());
                        } catch (ClassCastException e) {
                            System.err.println("[ServerModule Loader]: Failed to load main class for " + file.getName() + ": main class does not extend ServerModule.");
                        } catch (NoSuchMethodException | InvocationTargetException e) {
                            System.err.println("[ServerModule Loader]: Failed to load main class for " + file.getName() + ": " + e.getClass().getSimpleName() + " -> " + e.getMessage());
                        }
                    } catch (ClassNotFoundException e) {
                        if (className.equals("default")) {
                            System.err.println("[ServerModule Loader]: Failed to load main class for " + file.getName() + ": main class not specified.");
                        }
                        System.err.println("[ServerModule Loader]: Failed to load main class for " + file.getName() + ": ClassNotFoundException -> " + e.getMessage());
                    } catch (InstantiationException | IllegalAccessException e) {
                        System.err.println("[ServerModule Loader]: Failed to load main class for " + file.getName() + ": " + e.getClass().getSimpleName() + " -> " + e.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Properties getJarProperties(JarFile jar) throws IOException {
        InputStream stream = jar.getInputStream(jar.getJarEntry("module.properties"));
        Properties p = new Properties();
        p.load(stream);
        return p;
    }
}
