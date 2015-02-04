package org.blockserver.cmd;

import org.blockserver.Server;
import org.blockserver.ui.Logger;

public interface CommandIssuer{
	public void tell(String msg, Object... args);
	public static class ConsoleIssuer implements CommandIssuer{
		@SuppressWarnings("UnusedDeclaration")
		private Server server;
		private Logger logger;
		public ConsoleIssuer(Server server){
			this.server = server;
			this.logger = server.getLogger();
		}
		@Override
		public void tell(String msg, Object... args){
			if(args.length > 0){
				msg = String.format(msg, args);
			}
			logger.info(msg);
		}
	}
}
