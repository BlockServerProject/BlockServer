package net.blockserver.utility;

public class Utils
{
    public static void putBits(byte[] data, int offset, byte[] inByteArray, int numberOfBitsToWrite, boolean rightAlignedBits)
    {
        int numberOfBitsUsed = ((offset)<<3);
        final int numberOfBitsUsedMod8 = numberOfBitsUsed & 7;

        if (numberOfBitsUsedMod8==0 && (numberOfBitsToWrite&7)==0)
        {
            System.arraycopy(inByteArray,0, data, numberOfBitsUsed, (numberOfBitsToWrite>>3));
            numberOfBitsUsed+=numberOfBitsToWrite;
            return;
        }

        byte dataByte;
        byte[] inputPtr = inByteArray;

        int i = 0;
        while ( numberOfBitsToWrite > 0 )
        {
            dataByte = inputPtr[i++];

            if ( numberOfBitsToWrite < 8 && rightAlignedBits )
                dataByte <<= 8 - numberOfBitsToWrite;

            if ( numberOfBitsUsedMod8 == 0 )
                data[(numberOfBitsUsed >> 3)] = dataByte;
            else
            {
                data[(numberOfBitsUsed >> 3)] |= dataByte >> ( numberOfBitsUsedMod8 );

                if ( 8 - ( numberOfBitsUsedMod8 ) < 8 && 8 - ( numberOfBitsUsedMod8 ) < numberOfBitsToWrite )
                {
                    data[( numberOfBitsUsed >> 3 + 1 )] = (byte) ( dataByte << ( 8 - ( numberOfBitsUsedMod8 ) ) );
                }
            }

            if ( numberOfBitsToWrite >= 8 )
            {
                numberOfBitsUsed += 8;
                numberOfBitsToWrite -= 8;
            }
            else
            {
                numberOfBitsUsed += numberOfBitsToWrite;
                numberOfBitsToWrite=0;
            }
        }
    }

    public static byte[] getBits(byte[] data, int offset, int numberOfBitsToRead, boolean alignBitsToRight)
    {
        byte[] inOutByteArray = new byte[(((numberOfBitsToRead)+7)>>3)];
        int readOffset = ((offset)<<3);
        int numberOfBitsUsed = ((data.length)<<3);

        if (numberOfBitsToRead<=0)
            return inOutByteArray;

        if ( readOffset + numberOfBitsToRead > numberOfBitsUsed )
            return inOutByteArray;


        final int readOffsetMod8 = readOffset & 7;
        if (readOffsetMod8 == 0 && (numberOfBitsToRead & 7) == 0)
        {
            System.arraycopy(data, (readOffset >> 3), inOutByteArray, 0, inOutByteArray.length);
            readOffset+=numberOfBitsToRead;
            return inOutByteArray;
        }

        offset = 0;
        while ( numberOfBitsToRead > 0 )
        {
            inOutByteArray[offset] |= ( data [readOffset >> 3] ) << ( readOffsetMod8 );

            if ( readOffsetMod8 > 0 && numberOfBitsToRead > 8 - ( readOffsetMod8 ) )
                inOutByteArray[offset]|=  data[(readOffset >> 3)  + 1 ] >> ( 8 - ( readOffsetMod8 ) );

            if (numberOfBitsToRead>=8)
            {
                numberOfBitsToRead -= 8;
                readOffset += 8;
                offset++;
            }
            else
            {
                int neg = (int) numberOfBitsToRead - 8;

                if ( neg < 0 )
                {

                    if ( alignBitsToRight )
                        inOutByteArray[offset] >>= -neg;

                    readOffset += 8 + neg;
                }
                else
                    readOffset += 8;

                offset++;

                numberOfBitsToRead=0;
            }
        }
        return inOutByteArray;
    }


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
                        data[offset++]       |
                        data[offset++] << 8  |
                        data[offset] << 16
                    )
                );
    }

    public byte[] putTriad(int v) {
        return new byte[]{
                (byte) ((v & 0xFFFFFF) >> 16),
                (byte) ((v & 0xFFFF) >> 8),
                (byte) (v & 0xFF)
        };
    }

    public byte[] putLTriad(int v)
    {
        return new byte[] {
            (byte)  (v & 0xFF),
            (byte) ((v & 0xFFFF) >> 8),
            (byte) ((v & 0xFFFFFF) >> 16)
        };
    }
}
