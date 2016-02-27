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
import org.blockserver.core.modules.logging.LoggingModule;
import org.blockserver.core.modules.network.NetworkModule;
import org.blockserver.core.modules.player.PlayerModule;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.util.Map;

/**
 * @author BlockServer Team
 * @see org.blockserver.core.module.ModuleLoader
 */
public class CoreModuleLoader implements ModuleLoader {

    @Override
    public void setModules(Map<Class<? extends ServerModule>, ServerModule> modules, Server server) {
        int start = modules.size();
        LoggingModule loggingModule = new LoggingModule(server);
        SchedulerModule schedulerModule = new SchedulerModule(server);
        PlayerModule playerModule = new PlayerModule(server);
        NetworkModule networkModule = new NetworkModule(server);

        modules.put(loggingModule.getClass(), loggingModule);
        modules.put(schedulerModule.getClass(), schedulerModule);
        modules.put(playerModule.getClass(), playerModule);
        modules.put(networkModule.getClass(), networkModule);

        System.out.println("[ServerModule Loader]: Loaded " + (modules.size() - start) + " core modules.");
    }
}
