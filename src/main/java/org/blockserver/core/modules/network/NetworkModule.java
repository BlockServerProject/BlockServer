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
