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
	}

	@Override
	public void encode(){
		//TODO add Tiles to Chunk Packet
		byte[] compressed;
		try{
			ByteBuffer ab = ByteBuffer.allocate(0x400);
			for(int i: chunk.getBiomeColors()){
				ab.put((byte) (i & 0x0F));
				ab.put((byte) ((i << 8) & 0x0F));
				ab.put((byte) ((i << 16) & 0x0F));
				ab.put((byte) ((i << 24) & 0x0F));
			}
			//Why Buffer size is MiniChunk's size!?
			//Why use ByteBuffer!?
			//Why replace LInt to Int!?
			compressed = Utils.compressByte( Utils.writeLInt_( chunk.getX() ),Utils.writeLInt_( chunk.getZ() ), chunk.getBlocks(),
					chunk.getDamages(), chunk.getSkyLights(), chunk.getBlockLights(), chunk.getBiomeIds(), ab.array() );
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
