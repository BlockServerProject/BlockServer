package net.blockserver.network;

public class RaknetsID
{
    public static final int STRUCTURE = 5;
    public static byte[] MAGIC = { (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0xfe, (byte)0xfe, (byte)0xfe, (byte)0xfe, (byte)0xfd, (byte)0xfd, (byte)0xfd, (byte)0xfd, (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78 };

    public static final byte UNCONNECTED_PING = (byte)0x01;
    public static final byte UNCONNECTED_PING_OPEN_CONNECTIONS = (byte)0x02;

    public static final byte OPEN_CONNECTION_REQUEST_1 = (byte)0x05;
    public static final byte OPEN_CONNECTION_REPLY_1 = (byte)0x06;
    public static final byte OPEN_CONNECTION_REQUEST_2 = (byte)0x07;
    public static final byte OPEN_CONNECTION_REPLY_2 = (byte)0x08;

    public static final byte INCOMPATIBLE_PROTOCOL_VERSION = (byte)0x1a; //CHECK THIS

    public static final byte UNCONNECTED_PONG = (byte)0x1c;
    public static final byte ADVERTISE_SYSTEM = (byte)0x1d;

    public static final byte DATA_PACKET_0 = (byte)0x80;
    public static final byte DATA_PACKET_1 = (byte)0x81;
    public static final byte DATA_PACKET_2 = (byte)0x82;
    public static final byte DATA_PACKET_3 = (byte)0x83;
    public static final byte DATA_PACKET_4 = (byte)0x84;
    public static final byte DATA_PACKET_5 = (byte)0x85;
    public static final byte DATA_PACKET_6 = (byte)0x86;
    public static final byte DATA_PACKET_7 = (byte)0x87;
    public static final byte DATA_PACKET_8 = (byte)0x88;
    public static final byte DATA_PACKET_9 = (byte)0x89;
    public static final byte DATA_PACKET_A = (byte)0x8a;
    public static final byte DATA_PACKET_B = (byte)0x8b;
    public static final byte DATA_PACKET_C = (byte)0x8c;
    public static final byte DATA_PACKET_D = (byte)0x8d;
    public static final byte DATA_PACKET_E = (byte)0x8e;
    public static final byte DATA_PACKET_F = (byte)0x8f;

    public static final byte NACK = (byte)0xa0;
    public static final byte ACK = (byte)0xc0;
}