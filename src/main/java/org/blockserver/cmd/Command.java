package org.blockserver.cmd;

import java.util.ArrayList;

public abstract class Command{
	public abstract String getName();
	public abstract String getDescription(CommandIssuer issuer);
	public abstract String getUsage(CommandIssuer issuer);
	public void printUsage(CommandIssuer issuer){
		issuer.tell("Usage: " + getUsage(issuer));
	}

	public abstract String run(CommandIssuer issuer, ArrayList<String> args);

	@SuppressWarnings("UnusedParameters")
	public boolean canUse(CommandIssuer issuer){
		return true;
	}
}
