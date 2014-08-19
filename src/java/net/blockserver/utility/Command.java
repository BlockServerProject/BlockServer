package net.blockserver.utility;

public abstract class Command {
	public abstract String getName();
	public abstract void run(CommandIssuer issuer, String[] args);
}
