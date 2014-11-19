package org.blockserver;

import org.blockserver.Server;
import org.blockserver.ServerBuilder;

public class run{
	public static void main(String[] args){
		ServerBuilder builder = new ServerBuilder();
		Server server = builder.build();
		server.start();
	}
}
