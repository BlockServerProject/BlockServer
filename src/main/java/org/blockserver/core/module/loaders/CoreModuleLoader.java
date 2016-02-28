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
import org.blockserver.core.modules.config.ConfigModule;
import org.blockserver.core.modules.file.FileModule;
import org.blockserver.core.modules.logging.LoggingModule;
import org.blockserver.core.modules.network.NetworkModule;
import org.blockserver.core.modules.player.PlayerModule;
import org.blockserver.core.modules.scheduler.SchedulerModule;
import org.blockserver.core.modules.thread.ExecutorModule;

import java.util.Map;

/**
 * @author BlockServer Team
 * @see org.blockserver.core.module.ModuleLoader
 */
public class CoreModuleLoader implements ModuleLoader {

    @Override
    public void setModules(Map<Class<? extends ServerModule>, ServerModule> modules, Server server) {
        int start = modules.size();

        //Logging Module
        LoggingModule loggingModule = new LoggingModule(server);
        loggingModule.info("[CoreModuleLoader]: LoggingModule online, continuing load with logging capabilities!");

        //No Depends
        FileModule fileModule = new FileModule(server);
        PlayerModule playerModule = new PlayerModule(server);
        NetworkModule networkModule = new NetworkModule(server);

        //Single Module Depends
        ConfigModule configModule = new ConfigModule(server, fileModule);
        ExecutorModule executorModule = new ExecutorModule(server, configModule);
        SchedulerModule schedulerModule = new SchedulerModule(server, executorModule);

        //Multiple Module Depends


        //Module Adds
        //No Depends
        modules.put(fileModule.getClass(), fileModule);
        modules.put(loggingModule.getClass(), loggingModule);
        modules.put(networkModule.getClass(), networkModule);
        modules.put(playerModule.getClass(), playerModule);

        //Single Module Depends
        modules.put(configModule.getClass(), configModule);
        modules.put(executorModule.getClass(), executorModule);
        modules.put(schedulerModule.getClass(), schedulerModule);

        loggingModule.info("[CoreModuleLoader]: Loaded " + (modules.size() - start) + " core modules.");
    }
}