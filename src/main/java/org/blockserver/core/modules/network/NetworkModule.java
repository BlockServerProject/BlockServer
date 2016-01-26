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
import org.blockserver.core.events.RawPacketHandleEvent;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.message.Message;
import org.blockserver.core.modules.player.Player;
import org.blockserver.core.modules.player.PlayerModule;
import org.blockserver.core.modules.scheduler.SchedulerModule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The NetworkModule manages the various {@linkplain NetworkProvider}s and
 * handles the conversions of packets to messages. It also handles sending packets.
 *
 * @author BlockServer Team
 * @see NetworkProvider
 */
public class NetworkModule extends Module{
    private SchedulerModule scheduler;
    private Thread processorThread;
    //private ExecutorService networkOperationsPool = Executors.newFixedThreadPool(4); //TODO: set in config
    private List<NetworkProvider> registeredProviders = new ArrayList<>();

    public NetworkModule(Server server, SchedulerModule scheduler) {
        super(server);
        this.scheduler = scheduler;

        processorThread = new Thread(() -> {
            Thread.currentThread().setName("NetworkProcessor");
            registeredProviders.forEach(provider -> {
                RawPacket packet;
                int max = 250;
                while(((packet = provider.getNextPacket()) != null) && max-- > 0) { //while the provider has packets in the queue AND number of packets processed is less than 250
                    getServer().getEventManager().fire(new RawPacketHandleEvent(packet)); //TODO: not sure what I'm doing here - jython234
                }
            });
        });
        processorThread.start();
    }

    /**
     * Sends the specified {@linkplain RawPacket}s through the specified provider.
     * @param provider The {@linkplain NetworkProvider} to send the packets.
     * @param packets The {@linkplain RawPacket}s to be sent.
     */
    public void sendPackets(NetworkProvider provider, RawPacket... packets) {

    }

    /**
     * Sends the specified {@linkplain Message}s through the specified provider.
     * @param provider The {@linkplain NetworkProvider} to send the packets.
     * @param messages The {@linkplain Message}s to be sent.
     */
    public void sendMessages(NetworkProvider provider, Message... messages) {

    }

    public void registerProvider(NetworkProvider provider) {
        if(!registeredProviders.contains(provider)) {
            registeredProviders.add(provider);
            return;
        }
        throw new IllegalArgumentException("NetworkProvider is already registered!");
    }
}
