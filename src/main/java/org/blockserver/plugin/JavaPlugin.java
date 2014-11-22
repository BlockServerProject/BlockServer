package org.blockserver.plugin;

import org.blockserver.Server;

public abstract class JavaPlugin{
	private Server server;
	
	public JavaPlugin(Server server){
		this.server = server;
	}
	
	/**
	 * Get the server instance
	 * @return The Server instance 
	 */
	public Server getServer(){
		return server;
	}
}
