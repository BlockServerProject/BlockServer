package org.blockserver.api.impl;

import org.blockserver.api.API;
import org.blockserver.api.PluginManager;
import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket;
import org.blockserver.net.protocol.pe.login.RaknetSentCustomPacket;
import org.blockserver.player.Player;

/**
 * API class to be used when no API is set.
 */
public class DummyAPI implements API{

    @Override
    public boolean onPlayerJoin(Player player, Argument<String> joinMessage) {
        return true;
    }

    @Override
    public boolean onPlayerQuit(Player player, Argument<String> leaveMessage) {
        return true;
    }

    @Override
    public boolean onDataPacketSent(Player player, Argument<RaknetSentCustomPacket.SentEncapsulatedPacket> packet) {
        return true;
    }

    @Override
    public boolean onDataPacketReceived(Player player, Argument<RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket> packet, Argument<Boolean> handled) {
        return true;
    }

    @Override
    public PluginManager getPluginManager() {
        return null;
    }
}
