package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.Server;
import org.blockserver.net.protocol.pe.sub.v20.PeSubprotocolV20;

/**
 * Subprotocol for MCPE ALPHA 0.11.0 (Protocol 27)
 */
public class PeSubprotocolV27 extends PeSubprotocolV20{
    public PeSubprotocolV27(Server server) {
        super(server);
    }

    @Override
    public int getSubprotocolVersionId(){
        return 27;
    }

    @Override
    public String getSubprotocolName(){
        return "V27 compatible for MCPE alpha 0.11.0";
    }
}
