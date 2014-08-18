package net.blockserver.network.minecraft;

public class PacketsID
{
    public static final int CURRENT_PROTOCOL = 18;

    public static final byte PING = (byte)0x00;

    public static final byte PONG = (byte)0x03;

    public static final byte CLIENT_CONNECT = (byte)0x09;
    public static final byte SERVER_HANDSHAKE = (byte)0x10;

    public static final byte CLIENT_HANDSHAKE = (byte)0x13;

    public static final byte SERVER_FULL = (byte)0x14;
    public static final byte DISCONNECT = (byte)0x15;

    public static final byte BANNED = (byte)0x17;

    public static final byte LOGIN = (byte)0x82;
    public static final byte LOGIN_STATUS = (byte)0x83;
    public static final byte READY = (byte)0x84;
    public static final byte MESSAGE = (byte)0x85;
    public static final byte SET_TIME = (byte)0x86;
    public static final byte START_GAME = (byte)0x87;
    public static final byte ADD_MOB = (byte)0x88;
    public static final byte ADD_PLAYER = (byte)0x89;
    public static final byte REMOVE_PLAYER = (byte)0x8a;

    public static final byte ADD_ENTITY = (byte)0x8c;
    public static final byte REMOVE_ENTITY = (byte)0x8d;
    public static final byte ADD_ITEM_ENTITY = (byte)0x8e;
    public static final byte TAKE_ITEM_ENTITY = (byte)0x8f;
    public static final byte MOVE_ENTITY = (byte)0x90;

    public static final byte MOVE_ENTITY_POSROT = (byte)0x93;
    public static final byte ROTATE_HEAD = (byte)0x94;
    public static final byte MOVE_PLAYER = (byte)0x95;
    public static final byte PLACE_BLOCK = (byte)0x96;
    public static final byte REMOVE_BLOCK = (byte)0x97;
    public static final byte UPDATE_BLOCK = (byte)0x98;
    public static final byte ADD_PAINTING = (byte)0x99;
    public static final byte EXPLODE = (byte)0x9a;
    public static final byte LEVEL_EVENT = (byte)0x9b;
    public static final byte TILE_EVENT = (byte)0x9c;
    public static final byte ENTITY_EVENT = (byte)0x9d;
    public static final byte REQUEST_CHUNK = (byte)0x9e;
    public static final byte CHUNK_DATA = (byte)0x9f;
    public static final byte PLAYER_EQUIPMENT = (byte)0xa0;
    public static final byte PLAYER_ARMOR_EQUIPMENT = (byte)0xa1;
    public static final byte INTERACT = (byte)0xa2;
    public static final byte USE_ITEM = (byte)0xa3;
    public static final byte PLAYER_ACTION = (byte)0xa4;

    public static final byte HURT_ARMOR = (byte)0xa6;
    public static final byte SET_ENTITY_DATA = (byte)0xa7;
    public static final byte SET_ENTITY_MOTION = (byte)0xa8;
    public static final byte SET_ENTITY_LINK = (byte)0xa9;
    public static final byte SET_HEALTH = (byte)0xaa;
    public static final byte SET_SPAWN_PODSITION = (byte)0xab;
    public static final byte ANIMATE = (byte)0xac;
    public static final byte RESPAWN = (byte)0xad;
    public static final byte SEND_INVENTORY = (byte)0xae;
    public static final byte DROP_ITEM = (byte)0xaf;
    public static final byte CONTAINER_OPEN = (byte)0xb0;
    public static final byte CONTAINER_CLOSE = (byte)0xb1;
    public static final byte CONTAINER_SET_SLOT = (byte)0xb2;
    public static final byte CONTAINER_SET_DATA = (byte)0xb3;
    public static final byte CONTAINER_SET_CONTENT = (byte)0xb4;
    public static final byte CONTAINER_ACK = (byte)0xb5;
    public static final byte CHAT = (byte)0xb6;
    public static final byte ADVENTURE_SETTINGS = (byte)0xb7;
    public static final byte ENTITY_DATA = (byte)0xb8;
    public static final byte PLAYER_INPUT = (byte)0xb9;
}