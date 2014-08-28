package org.blockserver.cmd;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
	public String[] getAliases(){
		String[] array = aliases();
		String single = alias();
		List<String> aliases = new ArrayList<String>();
		if(array instanceof String[]){
			for(String alias: array){
				aliases.add(alias);
			}
		}
		if(single instanceof String){
			aliases.add(single);
		}
		return (String[]) aliases.toArray();
	}
	protected String[] aliases(){
		return new String[0];
	}
	protected String alias(){
		return null;
	}

	public abstract String getName();
	public abstract String run(CommandIssuer issuer, String[] args);
}
