package org.blockserver.cmd;

public class StopCommand extends Command{
	@Override
	public String getName(){
		return "stop";
	}

	@Override
	public String run(CommandIssuer issuer, String[] args){
		try{
			issuer.getServer().stop();
			return "Stopping server...";
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
