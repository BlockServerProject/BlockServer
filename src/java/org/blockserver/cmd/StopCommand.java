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
			if(args.size() >= 1){
				String arg = args.remove(0);
				try{
					int status = Integer.parseInt(arg);
					System.out.println(String.format("Exiting with %d", status));
					System.exit(status);
				}
				catch(NumberFormatException e){
					return null;
				}
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
