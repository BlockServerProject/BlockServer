package org.blockserver.level.provider.anvil;

import java.io.File;

import org.blockserver.Server;
import org.blockserver.level.provider.LevelProviderType;

public class AnvilLevelProviderType extends LevelProviderType<AnvilLevelProvider>{
	@Override
	public Class<AnvilLevelProvider> getProviderClass(){
		return AnvilLevelProvider.class;
	}

	@Override
	public boolean supports(File worldDir){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AnvilLevelProvider instantiate(Server server, File worldDir, String internalName){
		return new AnvilLevelProvider(server, worldDir, internalName);
	}
}
