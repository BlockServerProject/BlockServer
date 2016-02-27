package org.blockserver.core.modules.network.pipeline;

import org.blockserver.core.modules.network.pipeline.packet.RawPacket;

/**
 * Created by Exerosis.
 */
public interface PipelineProvider {
    void provide(RawPacket packet);
}
