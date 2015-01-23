package org.blockserver.ui;

public interface ConsoleIn{
	public String read();
	public void close(boolean emergency);
}
