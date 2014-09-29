package org.blockserver.level.format.bsl;

import java.io.File;

import org.blockserver.Server;
import org.blockserver.level.format.LevelProviderType;

public class BSLLevelProviderType extends LevelProviderType<BSLLevelProvider>{
	@Override
	public Class<BSLLevelProvider> getProviderClass(){
		return BSLLevelProvider.class;
	}
	@Override
	public boolean isValid(File worldDir){
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public BSLLevelProvider instantiate(Server server, File worldDir){
		return new BSLLevelProvider(server, worldDir);
	}
}
