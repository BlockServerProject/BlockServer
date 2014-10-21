package org.blockserver.level.provider.bsl;

import java.io.File;

import org.blockserver.Server;
import org.blockserver.level.provider.LevelProviderType;

public class BSLLevelProviderType extends LevelProviderType<BSLLevelProvider>{
	@Override
	public Class<BSLLevelProvider> getProviderClass(){
		return BSLLevelProvider.class;
	}
	@Override
	public boolean supports(File worldDir){
		if(!worldDir.exists()){
			return true;
		}
		if(worldDir.isFile()){
			return false;
		}
		return new File(worldDir, "index.bsf").isFile();
	}
	@Override
	public BSLLevelProvider instantiate(Server server, File worldDir, String name){
		return new BSLLevelProvider(server, worldDir, name);
	}
}
