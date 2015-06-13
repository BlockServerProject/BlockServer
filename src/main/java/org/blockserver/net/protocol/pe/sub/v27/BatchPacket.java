package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * MC_BATCH_PACKET (0xb1)
 */
public class BatchPacket extends PeDataPacket{
    public byte[] payload;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_BATCH);
        writer.writeInt(payload.length);
        writer.write(payload);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        int size = reader.readInt();
        if(size < 1 || size > reader.getInputStream().available()){
            payload = reader.read(reader.getInputStream().available());
        } else {
            payload = reader.read(size);
        }
    }

    /**
     * Create a new BatchPacket based on an existing buffer. The data will be compressed.
     * @param buffer Existing buffer.
     * @return New BatchPacket instance.
     */
    public static BatchPacket fromBuffer(byte[] buffer){
        BatchPacket bp = new BatchPacket();
        bp.payload = new byte[buffer.length];

        Deflater compresser = new Deflater();
        compresser.setInput(buffer);
        compresser.finish();
        int dataLen = compresser.deflate(bp.payload);
        compresser.end();

        bp.payload = Arrays.copyOf(bp.payload, dataLen);
        return bp;
    }

    /**
     * Create a new BatchPacket instance based on an existing BatchPacket recieved. The data will be decompressed.
     * @param buffer BatchPacket buffer.
     * @return BatchPacket instance.
     */
    public static BatchPacket decodeExisting(byte[] buffer) throws DataFormatException {
        BatchPacket bp = new BatchPacket();
        bp.decode(buffer);

        Inflater decompresser = new Inflater();
        decompresser.setInput(bp.payload);
        byte[] result = new byte[bp.payload.length];
        int len = decompresser.inflate(result);
        decompresser.end();

        bp.payload = Arrays.copyOf(result, len);

        return bp;
    }

    @Override
    protected int getLength() {
        return 5 + payload.length;
    }
}
