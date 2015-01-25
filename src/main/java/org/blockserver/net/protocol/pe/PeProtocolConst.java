package org.blockserver.net.protocol.pe;

import java.util.Random;

public interface PeProtocolConst{
	/**
	 * Server ID sent to clients.
	 * This ID is the same in every runtime, but also random.
	 */
	public static long SERVER_ID = new Random().nextLong();
	/**
	 * Current RakNet protocol version used by MCPE.<br>
	 * Since there is so far only one RakNet protocol version used, we will not handle dynamic RakNet versions;
	 */
	public final static byte RAKNET_PROTOCOL_VERSION = 5;
	public final static byte[] MAGIC = new byte[] {0x00, (byte) 0xff, (byte) 0xff, 0x00, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, 0x12, 0x34, 0x56, 0x78};
	public final static byte RAKNET_BROADCAST_PING_1                =       0x01;
	public final static byte RAKNET_BROADCAST_PING_2                =       0x02;
	public final static byte RAKNET_OPEN_CONNECTION_REQUEST_1       =       0x05;
	public final static byte RAKNET_OPEN_CONNECTION_REPLY_1         =       0x06;
	public final static byte RAKNET_OPEN_CONNECTION_REQUEST_2       =       0x07;
	public final static byte RAKNET_OPEN_CONNECTION_REPLY_2         =       0x08;
	public final static byte RAKNET_INCOMPATIBLE_PROTOCOL_VERSION   =       0x1A;
	public final static byte RAKNET_BROADCAST_PONG_1                =       0x1C;
	public final static byte RAKNET_BROADCAST_PONG_2                =       0x1D;
	public final static byte RAKNET_CUSTOM_PACKET_MIN               = (byte)0x80;
	public final static byte RAKNET_CUSTOM_PACKET_MAX               = (byte)0x8F;
	public final static byte RAKNET_CUSTOM_PACKET_DEFAULT           = (byte)0x84;
	public final static byte RAKNET_NACK                            = (byte)0xA0;
	public final static byte RAKNET_ACK                             = (byte)0xC0;
	public final static byte[] RAKNET_HAS_MESSAGE_RELIABILITIES = {
		0x02, 0x03, 0x04, 0x06, 0x07
	};
	public final static byte[] RAKNET_HAS_ORDER_RELIABILITIES = {
		0x01, 0x03, 0x04, 0x07
	};
	
	public final static byte MC_CLIENT_CONNECT = (byte) 0x09;
	public final static byte MC_SERVER_HANDSHAKE = (byte) 0x10;
	public final static byte MC_CLIENT_HANDSHAKE = (byte) 0x13;

	public final static byte MC_PLAY_PING = (byte) 0x00;
	public final static byte MC_PLAY_PONG = (byte) 0x03;

	public final static byte MC_DISCONNECT = (byte) 0x15;
	public final static byte MC_LOGIN_PACKET = (byte) 0x82;
	public final static byte MC_LOGIN_STATUS_PACKET = (byte) 0x83;
	public final static byte MC_MESSAGE_PACKET = (byte) 0x85;
	public final static byte MC_SET_TIME_PACKET = (byte) 0x86;
	public final static byte MC_START_GAME_PACKET = (byte) 0x87;
	public final static byte MC_ADD_MOB_PACKET = (byte) 0x88;
	public final static byte MC_ADD_PLAYER_PACKET = (byte) 0x89;
	public final static byte MC_REMOVE_PLAYER_PACKET = (byte) 0x8a;
	public final static byte MC_ADD_ENTITY_PACKET = (byte) 0x8c;
	public final static byte MC_REMOVE_ENTITY_PACKET = (byte) 0x8d;
	public final static byte MC_ADD_ITEM_ENTITY_PACKET = (byte) 0x8e;
	public final static byte MC_TAKE_ITEM_ENTITY_PACKET = (byte) 0x8f;
	public final static byte MC_MOVE_ENTITY_PACKET = (byte) 0x90;
	public final static byte MC_ROTATE_HEAD_PACKET = (byte) 0x94;
	public final static byte MC_MOVE_PLAYER_PACKET = (byte) 0x95;
	public final static byte MC_REMOVE_BLOCK_PACKET = (byte) 0x97;
	public final static byte MC_UPDATE_BLOCK_PACKET = (byte) 0x98;
	public final static byte MC_ADD_PAINTING_PACKET = (byte) 0x99;
	public final static byte MC_EXPLODE_PACKET = (byte) 0x9A;
	public final static byte MC_LEVEL_EVENT_PACKET = (byte) 0x9B;
	public final static byte MC_TILE_EVENT_PACKET = (byte) 0x9C;
	public final static byte MC_ENTITY_EVENT_PACKET = (byte) 0x9D;
	public final static byte MC_PLAYER_EQUIPMENT_PACKET = (byte) 0xA0;
	public final static byte MC_PLAYER_ARMOR_EQUIPMENT_PACKET = (byte) 0xA1;
	public final static byte MC_INTERACT_PACKET = (byte) 0xA2;
	public final static byte MC_SET_HEALTH_PACKET = (byte) 0xaa;
	public final static byte MC_SET_DIFFICULTY_PACKET = (byte) 0xbc;
	public final static byte MC_SPAWN_POSITION_PACKET = (byte) 0xab;
	public final static byte MC_FULL_CHUNK_DATA = (byte) 0xba;
}
