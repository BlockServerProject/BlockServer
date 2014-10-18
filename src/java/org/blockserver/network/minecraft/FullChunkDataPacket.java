package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;
import org.blockserver.level.provider.IChunk;
import org.blockserver.utility.Utils;

public class FullChunkDataPacket extends BaseDataPacket{
	private IChunk chunk;
	
	public final static int UNCOMPRESSED_CHUNK_LENGTH = (0x04 * 2) + 0x8000 + (0x4000*3) + 0x100 + 0x400;
	public static final byte[] BIOMECOLOR = new byte[]{0x00 ,(byte) 0x85 ,(byte) 0xb2 ,0x4a};
	
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
			afterBuffer.put(BIOMECOLOR);
		}
		byte[] compressed;
		try{
			compressed = Utils.compressByte( Utils.LInt(chunk.getX()), Utils.LInt(chunk.getZ()), chunk.getBlocks(), chunk.getDamages(), chunk.getSkyLights(), chunk.getBlockLights(), afterBuffer.array() );
		}
		catch(Exception e){
			e.printStackTrace();
			return;
		}
		bb = ByteBuffer.allocate( 1 + compressed.length );
		bb.put( FULL_CHUNK_DATA_PACKET );
		bb.put(compressed);
	}
	@Override
	public void decode(){
		throw new UnsupportedOperationException();
	}
}
