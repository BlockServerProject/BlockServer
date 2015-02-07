package org.blockserver;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.blockserver.player.DummyPlayerDatabase;
import org.blockserver.ui.Log4j2ConsoleOut;
import org.blockserver.utils.ServerConfig;

public class run{
	public static File DIR;
	public static File modules;
	public static void main(String[] arguments){
		ArrayList<String> args = new ArrayList<>(Arrays.asList(arguments));
		ServerBuilder builder = new ServerBuilder();
		String includePath = "data";
		int pos = args.indexOf("--file");
		if(pos != -1 && pos != args.size() - 1){
			includePath = args.get(pos + 1);
		}
		DIR = new File(includePath);
		DIR.mkdirs();

		modules = new File("modules");
		modules.mkdirs();

		ServerConfig config = new ServerConfig(new File(includePath));
		String ip = config.getStringProperty(ServerConfig.PROPERTY_IP);
		try{
			builder.setAddress(InetAddress.getByName(ip));
			builder.setServerName(config.getStringProperty("motd"));
		}
		catch(UnknownHostException e){
			System.out.println("[CRITICAL] Cannot start server: " + ip + " is an invalid "
					+ "IP. If you don't know what it is, change it back to 'localhost' "
					+ "and start the server again.");
			System.exit(2);
		}
		builder.setPort(config.getIntProperty(ServerConfig.PROPERTY_PORT, 19132))
				.setConsoleOut(new Log4j2ConsoleOut())
				.setIncludePath(DIR)
				.setServerName(config.getStringProperty(ServerConfig.PROPERTY_SERVER_NAME))
				.setPlayerDatabase(new DummyPlayerDatabase())
				.setModulePath(modules);
		Server server = builder.build();
		server.start();
	}
}
