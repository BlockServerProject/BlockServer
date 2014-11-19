package org.blockserver;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.blockserver.ui.Log4j2ServerLogger;
import org.blockserver.utils.ServerConfig;

public class run{
	public static void main(String[] arguments){
		ArrayList<String> args = new ArrayList<String>(Arrays.asList(arguments));
		ServerBuilder builder = new ServerBuilder();
		String includePath = ".";
		int pos = args.indexOf("--file");
		if(pos != -1 && pos != args.size() - 1){
			includePath = args.get(pos + 1);
		}
		ServerConfig config = new ServerConfig(new File(includePath));
		String ip = config.getStringProperty(ServerConfig.PROPERTY_IP);
		try{
			builder.setAddress(InetAddress.getByName(ip));
		}
		catch(UnknownHostException e){
			System.out.println("[CRITICAL] Cannot start server: " + ip + " is an invalid "
					+ "IP. If you don't know what it is, change it back to 'localhost' "
					+ "and start the server again.");
			System.exit(2);
		}
		builder.setPort(config.getIntProperty(ServerConfig.PROPERTY_PORT, 19132));
		builder.setConsoleOut(new Log4j2ServerLogger());
		Server server = builder.build();
		server.start();
	}
}
