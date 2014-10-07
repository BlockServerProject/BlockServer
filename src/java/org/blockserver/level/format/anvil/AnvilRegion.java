package org.blockserver.level.format.anvil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AnvilRegion{
	private File file;
	private AnvilLevelProvider provider;
	public AnvilRegion(File file, AnvilLevelProvider provider){
		this.file = file;
		this.provider = provider;
	}
	public void load(){
		try{
			FileInputStream is = new FileInputStream(file);
			byte[] locations = new byte[4096];
			is.read(locations);
			for(int i = 0; i < 1024; i++){
				int chunkX = i & 0x1F;
				int chunkZ = (i >> 5) & 0x1F;
				int offset = (locations[i * 4] << 8) + (locations[i * 4 + 1] << 4) + locations[i * 4 + 2];
				byte sectorCount = locations[i * 3 + 3];
			}
			byte[] timestamps = new byte[4096];
			is.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	private int chunkOffset(int x, int z){
		x &= 0x1F;
		z &= 0x1F;
		return 4 * (x + z << 5);
	}
}
