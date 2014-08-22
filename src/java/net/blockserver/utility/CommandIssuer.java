package net.blockserver.utility;

public interface CommandIssuer {
    public void sendMessage(String msg);
    public void sudoCommand(String line);
}
