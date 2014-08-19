package net.blockserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.blockserver.utility.CommandIssuer;

public class ConsoleCommandHandler extends Thread implements CommandIssuer{
	private Server server;
	protected boolean running;
	
	public ConsoleCommandHandler(Server server){
		this.server = server;
		this.setName("ConsoleCommandHandler");
	}
	
	public void run(){
		server.getLogger().info("Console Handler started.");
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while(this.running){
			try {
				String input = console.readLine();
				sudoCommand(input.trim());
//				if(input.startsWith(Command.getCommandName(Command.FREE))){
//					long total = Runtime.getRuntime().totalMemory() / 1000000;
//					long free = Runtime.getRuntime().freeMemory() / 1000000;
//					long used = total - free;
//					
//					System.out.println("Allocated memory: "+total + " mb");
//					System.out.println("Free memory: "+free + " mb");
//					System.out.println("Memory in use: "+used + " mb");
//				}
//				else if(input.startsWith(Command.getCommandName(Command.STOP))){
//					try {
//						System.exit(0);
//					} catch (Exception e) {
//						server.getLogger().info("Failed to stop server: ");
//						e.printStackTrace();
//					}
//					
//				}
//				else if(input.startsWith(Command.getCommandName(Command.HELP))){
//					System.out.println("- Showing help page 1 of 1 (/help <pageNumber>) -");
//					System.out.println("/free: Shows the available space of memory.");
//					System.out.println("/help: Shows available commands.");
//					System.out.println("/stop: Stops the server.");
//				}
//				else{
//					server.getLogger().warning("Unknown command: "+input);
//				}
			} catch (IOException e) {
				this.server.getLogger().error("IOException in ConsoleCommand Thread:");
				e.printStackTrace();
			}
		}
	}
	
	public void Start(){
		this.running = true;
		this.start();
	}
	
	public void Stop(){
		this.running = false;
	}

    @Override
    public void sendMessage(String msg) {
        server.getLogger().info(msg);
    }

    @Override
    public void sudoCommand(String line) {
        // TODO Auto-generated method stub
    }

}
