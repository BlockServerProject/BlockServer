package org.blockserver.level;

import org.blockserver.Server;

public class LevelManager{
	private ILevel levelImpl;
	private Server server;

	public LevelManager(Server server){
		this.server = server;
	}
	public ILevel getLevelImplemenation(){
		return levelImpl;
	}
	public void setLevelImpl(ILevel level){
		levelImpl = level;
	}

	public Server getServer(){
		return server;
	}
}
