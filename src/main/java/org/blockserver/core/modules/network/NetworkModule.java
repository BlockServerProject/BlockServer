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

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.ServerModule;
import org.blockserver.core.modules.network.pipeline.NetworkPipelineHandler;

/**
 * Created by Exerosis.
 */
public class NetworkModule extends ServerModule {
    @Getter private NetworkPipelineHandler inboundHandler = new NetworkPipelineHandler();
    @Getter private NetworkPipelineHandler outboundHandler = new NetworkPipelineHandler();

    public NetworkModule(Server server) {
        super(server);
    }
}
