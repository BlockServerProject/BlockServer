package net.blockserver;

import net.blockserver.utility.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCommandHandler extends Thread {
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
            String input = "";
            try{
                input = console.readLine();

                Command command = Command.valueOf(input.split(" ")[0].toUpperCase());

                switch(command){
                    case FREE:
                        long total = Runtime.getRuntime().totalMemory() / 1000000;
                        long free = Runtime.getRuntime().freeMemory() / 1000000;
                        long used = total - free;

                        System.out.println("Allocated memory: "+total + " mb");
                        System.out.println("Free memory: "+free + " mb");
                        System.out.println("Memory in use: "+used + " mb");
                    case HELP:
                        System.out.println("- Showing help page 1 of 1 (/help <pageNumber>) -");

                        for(Command cmd : Command.values()){
                            System.out.println("/" + cmd + ": " + cmd.getDescription());
                        }
                    case STOP:
                        try {
                            System.exit(0);
                        } catch (Exception e) {
                            server.getLogger().info("Failed to stop server: ");
                            e.printStackTrace();
                        }
                    default:
                        server.getLogger().warning("Command is exist but not initialized yet.");
                }

            } catch(IllegalArgumentException iae){
                server.getLogger().warning("Unknown command: "+input);
                iae.printStackTrace();
            } catch (IOException ioe){
                server.getLogger().error("IOException in ConsoleCommand Thread:");
            } catch (Exception e){
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

}
