package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.blockserver.level.provider.IChunk;
import org.blockserver.utility.Utils;

public class FullChunkDataPacket extends BaseDataPacket{
	private IChunk chunk;
	
	public final static int UNCOMPRESSED_CHUNK_LENGTH = (0x04 * 2) + 0x8000 + (0x4000*3) + 0x100 + 0x400;
	public static final byte[] BIOME_COLOR = new byte[]{0x00 ,(byte) 0x85 ,(byte) 0xb2 ,0x4a};
	
	public FullChunkDataPacket(IChunk chunk){
		this.chunk = chunk;
		System.out.println( "FullChunk: " + chunk.getX() + ", " + chunk.getZ() );
	}

	@Override
	public void encode(){
		//TODO add Tiles to Chunk Packet
		//TODO handle BIOMECOLOR or BIOMEIDs
		ByteBuffer afterBuffer = ByteBuffer.allocate(0x100 + 0x400);
		int last = 0x100;
		while( afterBuffer.position() != last ) {
			afterBuffer.put((byte) 0xff);
		}
		last += 0x400;
		while( afterBuffer.position() != last ) {
			afterBuffer.put(BIOME_COLOR);
		}
		byte[] compressed;
		try{
			ByteBuffer bb = ByteBuffer.allocate(8 + 0x1000 + 0x800 * 3 + 0x100 + 0x500).order(ByteOrder.LITTLE_ENDIAN);
			bb.putInt(chunk.getX());
			bb.putInt(chunk.getZ());
			bb.put(chunk.getBlocks());
			bb.put(chunk.getDamages());
			bb.put(chunk.getSkyLights());
			bb.put(chunk.getBlockLights());
			bb.put(chunk.getBiomeIds());
			for(int i: chunk.getBiomeColors()){
				bb.put((byte) (i & 0x0F));
				bb.put((byte) ((i << 8) & 0x0F));
				bb.put((byte) ((i << 16) & 0x0F));
				bb.put((byte) ((i << 24) & 0x0F));
			}
			compressed = Utils.compressByte(bb.array());
		}
		catch(Exception e){
			e.printStackTrace();
			return;
		}
		bb = ByteBuffer.allocate(1 + compressed.length);
		bb.put(FULL_CHUNK_DATA_PACKET);
		bb.put(compressed);
	}
	@Override
	public void decode(){
		throw new UnsupportedOperationException();
	}
}
