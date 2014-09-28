package org.blockserver.cmd;

import java.util.List;

public class StopCommand extends Command{
	@Override
	public String getName(){
		return "stop";
	}

	@Override
	public String run(CommandIssuer issuer, List<String> args){
		try{
			issuer.getServer().getLogger().info("Stopping server...");
			issuer.getServer().stop();
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
