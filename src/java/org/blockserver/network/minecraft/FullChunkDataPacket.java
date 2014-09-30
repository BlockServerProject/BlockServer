package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;
import java.util.zip.Deflater;

import org.blockserver.level.format.IChunk;
import org.blockserver.utility.Utils;

public class FullChunkDataPacket extends BaseDataPacket{
	private IChunk chunk;
	private byte[] buffer;

	public FullChunkDataPacket(IChunk chunk){
		this.chunk = chunk;
	}

	@Override
	public void encode(){
		ByteBuffer bb = ByteBuffer.allocate(0x20000);
		bb.put(Utils.putLTriad(chunk.getX()));
		bb.put(Utils.putLTriad(chunk.getZ()));
		bb.put(chunk.getBlocks());
		bb.put(chunk.getDamages());
		bb.put(chunk.getSkyLights());
		bb.put(chunk.getBlockLights());
		bb.put(chunk.getTiles());

		Deflater deflater = new Deflater(7);
		deflater.setInput(bb.array());
		deflater.finish();
		buffer = new byte[(int) deflater.getBytesWritten()];
		int length = deflater.deflate(buffer);
		if(length == 0){
			throw new RuntimeException("Length unexpectedly 0");
		}
		deflater.end();
	}
	@Override
	public void decode(){
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBuffer(){
		return buffer;
	}
}
