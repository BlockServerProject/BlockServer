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
package org.blockserver.core;

import org.blockserver.core.event.EventListener;
import org.blockserver.core.events.modules.ModuleEnableEvent;
import org.blockserver.core.module.loaders.CoreModuleLoader;
import org.blockserver.core.module.loaders.JarModuleLoader;
import org.blockserver.core.modules.logging.LoggingServerModule;

/**
 * Main class for the core.
 *
 * @author BlockServer team
 */
public class run {

    public static void main(String[] args) {
        Server server = new Server(new CoreModuleLoader(), new JarModuleLoader());

        new EventListener<ModuleEnableEvent, ModuleEnableEvent>() {
            @Override
            public void onEvent(ModuleEnableEvent event) {
            }
        }.register(ModuleEnableEvent.class, server.getEventManager());

        Runtime.getRuntime().addShutdownHook(new Thread(server::onDisable));
        server.onEnable();
        server.getModule(LoggingServerModule.class).info("BlockServer is now running.");
    }
}