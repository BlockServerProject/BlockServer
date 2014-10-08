package org.blockserver.level.format.anvil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.blockserver.io.BinaryUtils;
import org.blockserver.io.nbt.NBTReader;

public class AnvilRegion{
	private File file;
	private AnvilLevelProvider provider;
	private ChunkLoc[] chunkLocs = new ChunkLoc[1024];
	private byte[] chunksBuffer;
	public AnvilRegion(File file, AnvilLevelProvider provider){
		this.file = file;
		this.provider = provider;
	}
	public void load(){
		try{
			FileInputStream is = new FileInputStream(file);
			byte[] locations = new byte[4096];
			is.read(locations);
			byte[] timestamps = new byte[4096];
			
			for(int i = 0; i < 1024; i++){
				int offset = (locations[i * 4] << 8) + (locations[i * 4 + 1] << 4) + locations[i * 4 + 2];
				byte sectorCount = locations[i * 3 + 3];
				int timestamp = (int) BinaryUtils.read(timestamps, i * 4, 4);
				chunkLocs[i] = new ChunkLoc(offset, sectorCount, timestamp);
			}
			chunksBuffer = new byte[is.available()];
			is.read(chunksBuffer);
			is.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public AnvilChunk read(int x, int z) throws IOException{
		if(!isGenerated(x, z)){
			return null;
		}
		int offset = chunkOffset(x, z);
		ChunkLoc loc = chunkLocs[offset];
		int pointer = loc.offset << 12;
		int length = (int) BinaryUtils.read(chunksBuffer, pointer, 4); pointer += 4;
		byte comp = chunksBuffer[pointer++];
		ByteArrayInputStream arrayIs = new ByteArrayInputStream(chunksBuffer, pointer, length);
		InflaterInputStream is;
		if(comp == 1){
			is = new GZIPInputStream(arrayIs);
		}
		else{
			if(comp != 2){
				provider.getServer().getLogger().warning(
						"Level %s is corrupted: chunk %d (x=%d, z=%d) in region file %s is "
						+ "of unsupported compression type (%d)! Will assume as ZLIB.",
						provider.getLevel().getName(), offset, x, z, file.getCanonicalPath(), comp);
				comp = 2;
			}
			is = new InflaterInputStream(arrayIs);
		}
		return new AnvilChunk(new NBTReader(is), provider.getLevel());
	}
	public boolean isGenerated(int x, int z){
		ChunkLoc loc = chunkLocs[chunkOffset(x, z)];
		return loc.offset != 0 && loc.sectorCnt != 0;
	}
	private int chunkOffset(int x, int z){
		x &= 0x1F;
		z &= 0x1F;
		return 4 * (x + z << 5);
	}

	public static class ChunkLoc{
		private int offset;
		private byte sectorCnt;
		private int timestamp;
		protected ChunkLoc(int offset, byte sectorCnt, int timestamp){
			this.offset = offset;
			this.sectorCnt = sectorCnt;
			this.timestamp = timestamp;
		}
		public int getOffset(){
			return offset;
		}
		public byte getSectorCnt(){
			return sectorCnt;
		}
		public int getTimestamp(){
			return timestamp;
		}
	}
}
