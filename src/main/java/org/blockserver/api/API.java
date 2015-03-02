package org.blockserver.api;

import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket;
import org.blockserver.net.protocol.pe.login.RaknetSentCustomPacket;
import org.blockserver.player.Player;

/**
 * Represents an API. Must be implemented by ALL api extensions.
 * Return true for boolean functions unless you wanna refuse the event
 */
public interface API{
	public boolean onPlayerJoin(Player player, Argument<String> joinMessage);
	public boolean onPlayerQuit(Player player, Argument<String> leaveMessage);
	public boolean onDataPacketSent(Player player, Argument<RaknetSentCustomPacket.SentEncapsulatedPacket> packet);
	public boolean onDataPacketReceived(Player player, Argument<RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket> packet, Argument<Boolean> handled);

	public static class Argument<T>{
		public T value;
		public Argument(T value){
			this.value = value;
		}
	}

	public static class DummyAPI implements API{
		@Override
		public boolean onPlayerJoin(Player player,
				Argument<String> joinMessage){
			return true;
		}
		@Override
		public boolean onPlayerQuit(Player player, Argument<String> leaveMessage){
			return true;
		}

		@Override
		public boolean onDataPacketSent(Player player, Argument<RaknetSentCustomPacket.SentEncapsulatedPacket> packet){
			return true;
		}
		@Override
		public boolean onDataPacketReceived(Player player, Argument<RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket> packet, Argument<Boolean> handled){
			return true;
		}
	}
}
