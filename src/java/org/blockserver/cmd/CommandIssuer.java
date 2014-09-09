package org.blockserver.cmd;

import org.blockserver.Server;

public interface CommandIssuer {
	public void sendMessage(String msg);
	public void sudoCommand(String line);
	public int getHelpLines();
	public Server getServer();
}
