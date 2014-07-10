package net.blockserver.utility;

public class Utils
{
    public static int getTriad(byte[] data, int offset)
    {
        return (int)
                (
                    (
                        data[offset++] << 16 |
                        data[offset++] << 8  |
                        data[offset]
                    )
                );
    }

    public static int getLTriad(byte[] data, int offset)
    {
        return (int)
                (
                    (
                        data[offset++]      |
                        data[offset++] << 8 |
                        data[offset] << 16
                    )
                );
    }

    public static byte[] putTriad(int v) {
        return new byte[]{
                (byte) ((v & 0xFFFFFF) >> 16),
                (byte) ((v & 0xFFFF) >> 8),
                (byte) (v & 0xFF)
        };
    }

    public static byte[] putLTriad(int v)
    {
        return new byte[] {
            (byte)  (v & 0xFF),
            (byte) ((v & 0xFFFF) >> 8),
            (byte) ((v & 0xFFFFFF) >> 16)
        };
    }
}
