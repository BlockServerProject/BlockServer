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
package org.blockserver.core.modules.network;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.logging.LoggingModule;
import org.blockserver.core.modules.network.message.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manager for the network.
 *
 * @author BlockServer Team
 */
public class NetworkModule extends Module {
    private final LoggingModule loggingModule;
    private ExecutorService nio = Executors.newFixedThreadPool(2); //TODO: set in config
    private List<NetworkAdapter> adapters = new CopyOnWriteArrayList<>();

    public NetworkModule(Server server, LoggingModule loggingModule) {
        super(server);
        this.loggingModule = loggingModule;
    }

    public void onTick() {
        adapters.forEach(adapter -> {
            RawPacket packet = adapter.getProvider().getNextPacket();
            if(packet != null) {
                nio.execute(() -> {
                    Message message = adapter.packetToMessage(packet);

                });
            }
        });
    }

    @Override
    public void onEnable() {
        loggingModule.info("Network Module enabled.");
    }

    @Override
    public void onDisable() {
        //TODO: close adapters and providers
        loggingModule.info("Network Module disabled.");
    }

    @SuppressWarnings("unused")
    public void registerAdapter(NetworkAdapter adapter) {
        if(!adapters.contains(adapter)) {
            adapters.add(adapter);
        }
    }
}
