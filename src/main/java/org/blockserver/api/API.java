package org.blockserver.api;

import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket;
import org.blockserver.net.protocol.pe.login.RaknetSentCustomPacket;
import org.blockserver.player.Player;

/**
 * Represents an API. Must be implemented by ALL api extensions.
 */
public interface API {

    boolean onPlayerJoin(Player player, Argument<String> joinMessage);
    boolean onPlayerQuit(Player player, Argument<String> leaveMessage);
    boolean onDataPacketSent(Player player, Argument<RaknetSentCustomPacket.SentEncapsulatedPacket> packet);
    boolean onDataPacketReceived(Player player, Argument<RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket> packet, Argument<Boolean> handled);

    PluginManager getPluginManager();

    public static class Argument<T>{
        public T value;
        public Argument(T value){
            this.value = value;
        }
    }
}
